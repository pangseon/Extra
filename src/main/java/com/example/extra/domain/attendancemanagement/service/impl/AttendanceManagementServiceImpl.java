package com.example.extra.domain.attendancemanagement.service.impl;

import static org.springframework.util.ObjectUtils.isEmpty;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import com.example.extra.domain.attendancemanagement.exception.AlreadyClockedInException;
import com.example.extra.domain.attendancemanagement.exception.AlreadyClockedOutException;
import com.example.extra.domain.attendancemanagement.exception.AttendanceManagementErrorCode;
import com.example.extra.domain.attendancemanagement.exception.NotFoundAttendanceManagementException;
import com.example.extra.domain.attendancemanagement.mapper.entity.AttendanceManagementEntityMapper;
import com.example.extra.domain.attendancemanagement.repository.AttendanceManagementRepository;
import com.example.extra.domain.attendancemanagement.service.AttendanceManagementService;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.sample.exception.NotFoundTestException;
import com.example.extra.sample.exception.TestErrorCode;
import java.text.Collator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendanceManagementServiceImpl implements AttendanceManagementService {
    private final JobPostRepository jobPostRepository;
    private final AttendanceManagementRepository attendanceManagementRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final ApplicationRequestMemberRepository applicationRequestMemberRepository;
    private final AttendanceManagementEntityMapper attendanceManagementEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceManagementReadServiceResponseDto> getApprovedMemberInfo(
        final Long jobPostId,
        Pageable pageable
    ) {
        List<AttendanceManagementReadServiceResponseDto> attendanceManagementReadServiceResponseDtoList =
            getAttendanceManagementReadServiceResponseDtoList(jobPostId);

        sortAttendanceManagementReadServiceResponseDtoList(attendanceManagementReadServiceResponseDtoList, pageable);

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), attendanceManagementReadServiceResponseDtoList.size());
        return attendanceManagementReadServiceResponseDtoList.subList(start, end);
    }

    @Override
    @Transactional
    public void updateClockInTime(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    ) {
        AttendanceManagement attendanceManagement = getAttendanceManagementByJobPostIdAndRequestDto(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        if(Objects.nonNull(attendanceManagement.getClockInTime())){
            throw new AlreadyClockedInException(AttendanceManagementErrorCode.ALREADY_CLOCKED_IN);
        }
        attendanceManagement.updateClockInTimeTo(attendanceManagementUpdateServiceRequestDto.time());
    }

    @Override
    @Transactional
    public void updateClockOutTime(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    ) {
        AttendanceManagement attendanceManagement = getAttendanceManagementByJobPostIdAndRequestDto(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        if(Objects.nonNull(attendanceManagement.getClockOutTime())){
            throw new AlreadyClockedOutException(AttendanceManagementErrorCode.ALREADY_CLOCKED_OUT);
        }
        attendanceManagement.updateClockOutTimeTo(attendanceManagementUpdateServiceRequestDto.time());
    }

    @Override
    @Transactional
    public void updateMealCount(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    ) {
        AttendanceManagement attendanceManagement = getAttendanceManagementByJobPostIdAndRequestDto(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        attendanceManagement.addOneToMealCount();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AttendanceManagementCreateExcelServiceResponseDto> getExcelInfo(final Long jobPostId){
        JobPost jobPost = getJobPostById(jobPostId);
        List<AttendanceManagement> attendanceManagementList = attendanceManagementRepository.findAllByJobPost(jobPost);
        return attendanceManagementEntityMapper.toAttendanceManagementCreateExcelServiceResponseDtoList(attendanceManagementList);
    }

    private Member getMemberByNameAndBirthday(String memberName, LocalDate memberBirthday){
        return memberRepository.findByNameAndBirthday(
            memberName,
            memberBirthday
        ).orElseThrow(
            // TODO - member 의 NOT FOUND exception 이용
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
    }

    private JobPost getJobPostById(final Long jobPostId){
        return jobPostRepository.findById(jobPostId).orElseThrow(
            ()-> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST)
        );
    }

    private AttendanceManagement getAttendanceManagementByJobPostIdAndRequestDto(
        Long jobPostId,
        AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    ) {
        String memberName = attendanceManagementUpdateServiceRequestDto.memberName();
        LocalDate memberBirthday = attendanceManagementUpdateServiceRequestDto.memberBirthday();
        Member member = getMemberByNameAndBirthday(memberName, memberBirthday);
        JobPost jobPost = getJobPostById(jobPostId);
        return attendanceManagementRepository.findByMemberAndJobPost(
            member,
            jobPost
        ).orElseThrow(
            ()-> new NotFoundAttendanceManagementException(AttendanceManagementErrorCode.NOT_FOUND_ATTENDANCE_MANAGEMENT)
        );
    }

    private List<AttendanceManagementReadServiceResponseDto> getAttendanceManagementReadServiceResponseDtoList(final Long jobPostId){
        List<AttendanceManagementReadServiceResponseDto> attendanceManagementReadServiceResponseDtoList = new ArrayList<>();
        JobPost jobPost = getJobPostById(jobPostId);
        List<Role> roleList = roleRepository.findAllByJobPost(jobPost);
        for(Role role : roleList){
            List<ApplicationRequestMember> applicationRequestMemberList =
                applicationRequestMemberRepository.findAllByRoleAndApplyStatus(role, ApplyStatus.APPROVED);
            for(ApplicationRequestMember applicationRequestMember: applicationRequestMemberList){
                // 출석 여부 정보 확인
                AttendanceManagement attendanceManagement = attendanceManagementRepository.findByMemberAndJobPost(
                    applicationRequestMember.getMember(),jobPost).orElseThrow(
                    ()-> new NotFoundAttendanceManagementException(AttendanceManagementErrorCode.NOT_FOUND_ATTENDANCE_MANAGEMENT)
                );
                boolean isAttended = Objects.nonNull(attendanceManagement.getClockInTime());

                // dto 구성
                attendanceManagementReadServiceResponseDtoList.add(
                    AttendanceManagementReadServiceResponseDto.builder()
                        .memberId(applicationRequestMember.getMember().getId())
                        .memberName(applicationRequestMember.getMember().getName())
                        .roleName(role.getRoleName())
                        .isAttended(isAttended)
                        .build()
                );
            }
        }
        return attendanceManagementReadServiceResponseDtoList;
    }

    private void sortAttendanceManagementReadServiceResponseDtoList(
        List<AttendanceManagementReadServiceResponseDto> attendanceManagementReadServiceResponseDtoList,
        Pageable pageable
    ){
        Collator koreanCollator = Collator.getInstance(Locale.KOREAN);
        // List를 정렬
        if (!isEmpty(pageable.getSort())) {
            for (Sort.Order order : pageable.getSort()) {
                Comparator<AttendanceManagementReadServiceResponseDto> comparator =
                    switch (order.getProperty()) {
                    case "memberName" ->
                        (dto1, dto2) -> koreanCollator.compare(dto1.memberName(), dto2.memberName());
                    case "roleName" ->
                        Comparator.comparing(AttendanceManagementReadServiceResponseDto::roleName);
                    default -> throw new IllegalArgumentException(
                        "Invalid sort property: " + order.getProperty());
                };
                if (order.isDescending()) {
                    comparator = comparator.reversed();
                }
                attendanceManagementReadServiceResponseDtoList.sort(comparator);
            }
        }
    }
}
