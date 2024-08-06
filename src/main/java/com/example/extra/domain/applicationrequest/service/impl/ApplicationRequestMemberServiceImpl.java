package com.example.extra.domain.applicationrequest.service.impl;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestException;
import com.example.extra.domain.applicationrequest.mapper.entity.ApplicationRequestEntityMapper;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import com.example.extra.domain.attendancemanagement.repository.AttendanceManagementRepository;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import java.util.Objects;
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
    private final ApplicationRequestEntityMapper applicationRequestEntityMapper;
    private final AttendanceManagementRepository attendanceManagementRepository;

    // 출연자가 특정 역할에 지원할 때
    @Override
    @Transactional
    public void createApplicationRequestMember(
        final Member member,
        final Long roleId
    ) {
        Role role = getRoleById(roleId);
        Boolean isStillRecruiting = role.getJobPost().getStatus();
        // 모집이 마감된 경우 지원 불가
        if (!isStillRecruiting){
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_APPLY_TO_JOB_POST
            );
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
        final Member member,
        final Pageable pageable
    ) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMember(
                member,
                pageable
            );
        return applicationRequestEntityMapper.toApplicationRequestMemberReadServiceResponseDtoList(applicationRequestMemberSlice);
    }

    // 출연자가 특정 상태(지원중, 미승인, 승인)에 따라 본인이 지원한 역할들 보려 할 때
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final Member member,
        final ApplyStatus applyStatus,
        final Pageable pageable
    ) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMemberAndApplyStatus(
                member,
                applyStatus,
                pageable
            );
        return applicationRequestEntityMapper.toApplicationRequestMemberReadServiceResponseDtoList(applicationRequestMemberSlice);
    }

    // 출연자가 지원 요청을 취소할 때
    @Override
    @Transactional
    public void deleteApplicationRequestMember(
        final Member member,
        final Long applicationRequestMemberId
    ) {
        ApplicationRequestMember applicationRequestMember = getApplicationRequestMemberById(applicationRequestMemberId);
        // 본인이 지원한 역할에 대해서만 삭제 가능
        if (!Objects.equals(applicationRequestMember.getMember().getId(), member.getId())){
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        // 승인 상태에서 삭제 불가
        if (applicationRequestMember.getApplyStatus() == ApplyStatus.APPROVED){
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_CANCEL_IN_APPROVED);
        }
        Role role = applicationRequestMember.getRole();
        applicationRequestMemberRepository.delete(applicationRequestMember);
        role.subtractOneToCurrentPersonnel();
    }

    // 업체가 해당 역할에 지원한 출연자를 확인할 때(지원 상태와 무관)
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final Company company,
        final long roleId,
        final Pageable pageable
    ) {
        Role role = getRoleById(roleId);
        // 업체가 작성한 공고의 역할만 확인 가능
        if (!Objects.equals(role.getJobPost().getCompany().getId(), company.getId())){
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRole(
                role,
                pageable
            );
        return applicationRequestEntityMapper.toApplicationRequestCompanyReadServiceResponseDtoList(applicationRequestMemberSlice);
    }

    // 업체가 해당 역할에 지원한 출연자들에 대해 승인/거절할 때
    @Override
    @Transactional
    public void updateStatus(
        final Company company,
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    ) {
        ApplicationRequestMember applicationRequestMember = getApplicationRequestMemberById(applicationRequestMemberId);

        // 업체가 작성한 공고의 역할만 승인/거절 가능
        if (!Objects.equals(applicationRequestMember.getRole().getJobPost().getCompany().getId(), company.getId())){
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER);
        }
        ApplyStatus applyStatus = applicationRequestMemberUpdateServiceRequestDto.applyStatus();
        applicationRequestMember.updateStatusTo(applyStatus);
    }
    // 업체가 해당 역할에 지원한 출연자들에 대해 승인할 때 attendance management 추가
    @Override
    @Transactional
    public void createAttendanceManagementIfApproved(
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    ){
        ApplicationRequestMember applicationRequestMember = getApplicationRequestMemberById(applicationRequestMemberId);
        attendanceManagementRepository.save(
            AttendanceManagement.builder()
                .member(applicationRequestMember.getMember())
                .jobPost(applicationRequestMember.getRole().getJobPost())
                .clockInTime(null)
                .clockOutTime(null)
                .mealCount(0)
                .build()
        );
    }
    private Role getRoleById(final Long roleId){
        return roleRepository.findById(roleId).orElseThrow(
            ()-> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE)
        );
    }
    private ApplicationRequestMember getApplicationRequestMemberById(Long applicationRequestMemberId){
        return applicationRequestMemberRepository.findById(applicationRequestMemberId).orElseThrow(
            // 지원 상태 업데이트 하려 했는데 없으면 예외 발생.
            ()-> new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST_MEMBER
            )
        );
    }
}
