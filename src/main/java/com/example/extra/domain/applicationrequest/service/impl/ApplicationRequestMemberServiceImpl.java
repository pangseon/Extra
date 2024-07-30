package com.example.extra.domain.applicationrequest.service.impl;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.NotAbleToCancelApplicationRequestMemberException;
import com.example.extra.domain.applicationrequest.exception.NotAbleToApplyToJobPostException;
import com.example.extra.domain.applicationrequest.exception.NotFoundApplicationRequestCompanyException;
import com.example.extra.domain.applicationrequest.exception.NotFoundApplicationRequestMemberException;
import com.example.extra.domain.applicationrequest.mapper.entity.ApplicationRequestEntityMapper;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.sample.exception.NotFoundTestException;
import com.example.extra.sample.exception.TestErrorCode;
import java.util.List;
import java.util.Optional;
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
    private final MemberRepository memberRepository;
    private final ApplicationRequestEntityMapper applicationRequestEntityMapper;

    private Member getMember(){
        // TODO - token 통해 id 얻기
        return memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
    }
    private Role getRoleById(final Long roleId){
        return roleRepository.findById(roleId).orElseThrow(
            // TODO - role의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
    }
    // 출연자가 특정 역할에 지원할 때
    @Override
    @Transactional
    public void createApplicationRequestMember(final Long roleId) {
        Member member = getMember();
        Role role = getRoleById(roleId);
        Boolean isStillRecruiting = role.getJobPost().getStatus();
        // 모집이 마감된 경우 지원 불가
        if (!isStillRecruiting){
            throw new NotAbleToApplyToJobPostException(
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
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(final Pageable pageable) {
        Member member = getMember();
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
        final ApplyStatus applyStatus,
        final Pageable pageable
    ) {
        Member member = getMember();
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
    public void deleteApplicationRequestMember(final Long applicationRequestId) {
        Optional<ApplicationRequestMember> applicationRequestMember =
            applicationRequestMemberRepository.findById(
                applicationRequestId
            );
        // 지우려 했는데 없으면 예외 발생.
        applicationRequestMember.orElseThrow(
            ()-> new NotFoundApplicationRequestMemberException(
                ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST_MEMBER
            )
        );
        // 승인 상태에서 삭제 불가
        if (applicationRequestMember.get().getApplyStatus() == ApplyStatus.APPROVED){
            throw new NotAbleToCancelApplicationRequestMemberException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_CANCEL_APPLICATION_REQUEST_MEMBER
            );
        }
        Role role = applicationRequestMember.get().getRole();
        applicationRequestMemberRepository.delete(applicationRequestMember.get());
        role.subtractOneToCurrentPersonnel();
    }

    // 업체가 해당 역할에 지원한 출연자를 확인할 때(지원 상태와 무관)
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final long roleId,
        final Pageable pageable
    ) {
        Role role = getRoleById(roleId);
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRole(
                role,
                pageable
            );
        return applicationRequestEntityMapper.toApplicationRequestCompanyReadServiceResponseDtoList(applicationRequestMemberSlice);
    }

    // 업체가 해당 역할에 출연 확정된 출연자를 확인할 때
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestCompanyReadServiceResponseDto> getApprovedMembersByRole(
        final long roleId,
        final Pageable pageable
    ) {
        Role role = getRoleById(roleId);
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRoleAndApplyStatus(
                role,
                ApplyStatus.APPROVED,
                pageable
            );
        return applicationRequestEntityMapper.toApplicationRequestCompanyReadServiceResponseDtoList(applicationRequestMemberSlice);
    }

    // 업체가 해당 역할에 지원한 출연자들에 대해 승인/거절할 때
    @Override
    @Transactional
    public void updateStatus(
        final Long applicationRequestId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    ) {
        Optional<ApplicationRequestMember> applicationRequestMember =
            applicationRequestMemberRepository.findById(
                applicationRequestId
            );
        // 지원 상태 업데이트 하려 했는데 없으면 예외 발생.
        applicationRequestMember.orElseThrow(
            ()-> new NotFoundApplicationRequestCompanyException(
                ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST_COMPANY
            )
        );
        applicationRequestMember.get()
            .updateStatusTo(applicationRequestMemberUpdateServiceRequestDto.applyStatus());
    }
}
