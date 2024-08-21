package com.example.extra.domain.applicationrequest.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestException;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import com.example.extra.domain.attendancemanagement.exception.AttendanceManagementErrorCode;
import com.example.extra.domain.attendancemanagement.exception.AttendanceManagementException;
import com.example.extra.domain.attendancemanagement.repository.AttendanceManagementRepository;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.exception.RoleException;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.s3.S3Provider;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ApplicationRequestMemberServiceImpl implements ApplicationRequestMemberService {

    private final ApplicationRequestMemberRepository applicationRequestMemberRepository;
    private final RoleRepository roleRepository;
    private final AttendanceManagementRepository attendanceManagementRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Provider s3Provider;

    // 출연자가 특정 역할에 지원할 때
    @Override
    @Transactional
    public void createApplicationRequestMember(
        final Account account,
        final Long roleId
    ) {
        Member member = getMemberByAccount(account);
        Role role = getRoleById(roleId);

        // 이미 해당 공고에 지원한 경우 지원 불가
        applicationRequestMemberRepository.findByMemberAndRole_jobPostId(member, role.getJobPost().getId())
            .ifPresent(a -> {
                throw new ApplicationRequestException(ApplicationRequestErrorCode.ALREADY_EXIST);
            });

        // 모집이 마감된 경우 지원 불가
        Boolean isStillRecruiting = role.getJobPost().getStatus();
        if (!isStillRecruiting) {
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_APPLY_TO_JOB_POST_DUE_TO_STATUS);
        }

        // 촬영 날짜가 겹치는 다른 공고 역할에 지원한 경우 지원 불가
        List<ApplicationRequestMember> existingApplicationRequestMemberList =
            applicationRequestMemberRepository.findAllByMember(getMemberByAccount(account));
        List<Schedule> newRoleScheduleList = role.getJobPost().getScheduleList();
        for (ApplicationRequestMember existingRequest : existingApplicationRequestMemberList) {
            List<Schedule> existingScheduleList = existingRequest.getRole().getJobPost().getScheduleList();
            boolean isOverlap = existingScheduleList.stream()
                .anyMatch(existingSchedule ->
                    newRoleScheduleList.stream().anyMatch(newSchedule ->
                            existingSchedule.getCalender().equals(newSchedule.getCalender())
                    )
                );
            if (isOverlap) {
                throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_APPLY_TO_JOB_POST_DUE_TO_DATE);
            }
        }

        applicationRequestMemberRepository.save(
            ApplicationRequestMember.builder()
                .applyStatus(ApplyStatus.APPLIED)
                .member(member)
                .role(role)
                .build()
        );
        role.addOneToCurrentPersonnel();
    }

    // 출연자가 본인이 지원한 역할들 확인할 때
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(
        final Account account,
        final Integer year,
        final Integer month,
        final Pageable pageable
    ) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice;
        if (year != null && month != null) {
            List<ApplicationRequestMember> applicationRequestMemberList =
                applicationRequestMemberRepository.findAllByMember(getMemberByAccount(account));

            return applicationRequestMemberList.stream()
                .filter(applicationRequestMember -> {
                    Role role = applicationRequestMember.getRole();
                    JobPost jobPost = role.getJobPost();
                    return jobPost.getScheduleList().stream()
                        .anyMatch(schedule ->
                            schedule.getCalender().getYear() == year &&
                                schedule.getCalender().getMonthValue() == month
                        );
                })
                .map(ApplicationRequestMemberReadServiceResponseDto::from)
                .collect(Collectors.toList());

        } else {
            applicationRequestMemberSlice =
                applicationRequestMemberRepository.findAllByMember(
                    getMemberByAccount(account),
                    pageable
                );
            return applicationRequestMemberSlice.stream()
                .map(ApplicationRequestMemberReadServiceResponseDto::from)
                .collect(Collectors.toList());
        }
    }

    // 출연자가 특정 상태(지원중, 미승인, 승인)에 따라 본인이 지원한 역할들 보려 할 때
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final Account account,
        final ApplyStatus applyStatus,
        final Pageable pageable
    ) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMemberAndApplyStatus(
                getMemberByAccount(account),
                applyStatus,
                pageable
            );
        return applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMemberReadServiceResponseDto::from)
            .collect(Collectors.toList());
    }

    // 출연자가 지원 요청을 취소할 때
    @Override
    @Transactional
    public void deleteApplicationRequestMember(
        final Account account,
        final Long applicationRequestMemberId
    ) {
        Member member = getMemberByAccount(account);
        ApplicationRequestMember applicationRequestMember = getApplicationRequestMemberById(
            applicationRequestMemberId);
        // 본인이 지원한 역할에 대해서만 삭제 가능
        if (!Objects.equals(applicationRequestMember.getMember().getId(), member.getId())) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        // 승인 상태에서 삭제 불가
        if (applicationRequestMember.getApplyStatus() == ApplyStatus.APPROVED) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_CANCEL_IN_APPROVED);
        }
        Role role = applicationRequestMember.getRole();
        applicationRequestMemberRepository.delete(applicationRequestMember);
        role.subtractOneToCurrentPersonnel();
    }

    // 업체가 해당 역할에 지원한 출연자를 확인할 때(지원 상태와 무관)
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final Account account,
        final long roleId,
        final Pageable pageable
    ) {
        Company company = getCompanyByAccount(account);
        Role role = getRoleById(roleId);
        // 업체가 작성한 공고의 역할만 확인 가능
        if (!Objects.equals(role.getJobPost().getCompany().getId(), company.getId())) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRole(
                role,
                pageable
            );
        return applicationRequestMemberSlice.stream()
            .map(ApplicationRequestCompanyReadServiceResponseDto::from)
            .collect(Collectors.toList());
    }

    // 업체가 해당 역할에 지원한 출연자들에 대해 승인/거절할 때
    @Override
    @Transactional
    public void updateStatus(
        final Account account,
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    ) {
        Company company = getCompanyByAccount(account);
        ApplicationRequestMember applicationRequestMember = getApplicationRequestMemberById(applicationRequestMemberId);
        JobPost jobpost = applicationRequestMember.getRole().getJobPost();

        // 업체가 작성한 공고의 역할만 승인/거절 가능
        if (!company.equals(jobpost.getCompany())) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        ApplyStatus applyStatus = applicationRequestMemberUpdateServiceRequestDto.applyStatus();
        applicationRequestMember.updateStatusTo(applyStatus);

        // 업체가 해당 역할에 지원한 출연자들에 대해 승인할 때 attendance management 추가
        if (applyStatus == ApplyStatus.APPROVED) {
            Member member = applicationRequestMember.getMember();
            // 이미 승인된 출연자에 대해 승인할 때 예외 처리
            Optional<AttendanceManagement> attendanceManagementOptional = attendanceManagementRepository.findByMemberAndJobPost(
                member,
                jobpost
            );
            if (attendanceManagementOptional.isPresent()) {
                throw new AttendanceManagementException(AttendanceManagementErrorCode.ALREADY_EXIST);
            }
            attendanceManagementRepository.save(
                AttendanceManagement.builder()
                        .member(member)
                        .jobPost(applicationRequestMember.getRole().getJobPost())
                        .clockInTime(null)
                        .clockOutTime(null)
                        .mealCount(0)
                    .build()
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberReadServiceResponseDto readOnceApplicationRequestMember(
        final Account account,
        final Long applicationRequestId
    ) {
        // 해당 회사가 작성한 역할인지 확인
        // 아닌 경우 -> throw 접근 권한 없음
        Company company = getCompanyByAccount(account);
        ApplicationRequestMember applicationRequestMember =
            getApplicationRequestMemberById(applicationRequestId);

        if (!company.equals(applicationRequestMember.getRole().getJobPost().getCompany())) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER
            );
        }

        return MemberReadServiceResponseDto.from(
            applicationRequestMember.getMember(),
            s3Provider.getProfileImagePresignedUrl(applicationRequestMember.getMember().getAccount().getId())
        );
    }

    private Role getRoleById(final Long roleId) {
        return roleRepository.findById(roleId).orElseThrow(
            () -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE)
        );
    }

    private ApplicationRequestMember getApplicationRequestMemberById(
        Long applicationRequestMemberId) {
        return applicationRequestMemberRepository.findById(applicationRequestMemberId).orElseThrow(
            // 지원 상태 업데이트 하려 했는데 없으면 예외 발생.
            () -> new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST_MEMBER
            )
        );
    }

    private Member getMemberByAccount(final Account account) {
        return memberRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }
}
