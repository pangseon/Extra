package com.example.extra.domain.applicationrequest.service.impl;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.NotAbleToCancelApplicationRequestMemberException;
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
@Transactional
@Service
public class ApplicationRequestMemberServiceImpl implements ApplicationRequestMemberService {
    private final ApplicationRequestMemberRepository applicationRequestMemberRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final ApplicationRequestEntityMapper applicationRequestEntityMapper;

    // 출연자가 특정 역할에 지원할 때
    @Override
    public void createApplicationRequestMember(Long roleId) {
        // TODO - token 통해 id 얻기
        Member member = memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        Role role = roleRepository.findById(roleId).orElseThrow(
            // TODO - role의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
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
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(Pageable pageable) {
        Long memberId = 1L;
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMemberId(
                memberId,
                pageable
            );

        List<Role> roleList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getRole)
            .toList();

        return applicationRequestEntityMapper.toApplicationRequestMemberReadServiceResponseDtoList(roleList);
    }

    // 출연자가 특정 상태(지원중, 미승인, 승인)에 따라 본인이 지원한 역할들 보려 할 때
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final ApplyStatus applyStatus,
        Pageable pageable
    ) {
        Long memberId = 1L;
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMemberIdAndApplyStatus(
                memberId,
                applyStatus,
                pageable
            );
        List<Role> roleList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getRole)
            .toList();
        return applicationRequestEntityMapper.toApplicationRequestMemberReadServiceResponseDtoList(roleList);
    }

    // 출연자가 지원 요청을 취소할 때
    @Override
    public void deleteApplicationRequestMember(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(
            // TODO - role의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        Long memberId = 1L;
        Optional<ApplicationRequestMember> applicationRequestMember =
            applicationRequestMemberRepository.findByMemberIdAndRoleId(
                memberId,
                roleId
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
        applicationRequestMemberRepository.delete(applicationRequestMember.get());
        role.subtractOneToCurrentPersonnel();
    }

    // 업체가 해당 역할에 지원한 출연자를 확인할 때
    @Override
    public List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final long roleId,
        final Pageable pageable
    ) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRoleId(
                roleId,
                pageable
            );
        List<Member> memberList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getMember)
            .toList();
        return applicationRequestEntityMapper.toApplicationRequestCompanyReadServiceResponseDtoList(memberList);
    }

    @Override
    public List<ApplicationRequestCompanyReadServiceResponseDto> getApprovedMembersByRole(
        final long roleId, final Pageable pageable) {
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByRoleIdAndApplyStatus(
                roleId,
                ApplyStatus.APPROVED,
                pageable
            );
        List<Member> memberList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getMember)
            .toList();
        return applicationRequestEntityMapper.toApplicationRequestCompanyReadServiceResponseDtoList(memberList);
    }

    @Override
    public void updateStatus(
        final Long roleId,
        final Long memberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    ) {
        Optional<ApplicationRequestMember> applicationRequestMember =
            applicationRequestMemberRepository.findByMemberIdAndRoleId(
                memberId,
                roleId
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
