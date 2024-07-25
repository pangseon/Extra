package com.example.extra.domain.applicationrequest.service.impl;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCreateServiceRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestDeleteServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.AlreadyExistApplicationRequestMemberException;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.NotAbleToCancelApplicationRequestMemberException;
import com.example.extra.domain.applicationrequest.exception.NotFoundApplicationRequestMemberException;
import com.example.extra.domain.applicationrequest.mapper.entity.ApplicationRequestEntityMapper;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.entity.MemberRepository;
import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.mapper.service.RoleEntityMapper;
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
    private final RoleEntityMapper roleEntityMapper;

    // TODO - 전반적으로 Transaction이 길다. 나중에 쪼갤 수 있는 것은 쪼갤 것.

    // 출연자가 특정 역할에 지원할 때
    @Override
    public void createApplicationRequestMember(
        final ApplicationRequestCreateServiceRequestDto applicationRequestCreateServiceRequestDto
    ) {
        // TODO - token 통해 id 얻기
        Member member = memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        Role role = roleRepository.findById(applicationRequestCreateServiceRequestDto.roleId()).orElseThrow(
            // TODO - role의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        // 이미 지원한 경우에 대한 예외 처리
        Optional<ApplicationRequestMember> applicationRequestMemberFromDB =
            applicationRequestMemberRepository.findByMemberAndRole(
                member,
                role
            );

        applicationRequestMemberFromDB.ifPresent(
            i -> {
                throw new AlreadyExistApplicationRequestMemberException(
                    ApplicationRequestErrorCode.ALREADY_EXIST_APPLICATION_REQUEST_MEMBER
                );
            }
        );

        ApplicationRequestMember applicationRequestMember =
            applicationRequestEntityMapper.toApplicationRequestMemberFromCreateRequest(
                applicationRequestCreateServiceRequestDto,
                member,
                role
        );
        applicationRequestMemberRepository.save(applicationRequestMember);
    }

    // 출연자가 본인이 지원한 역할들 확인하려 할 때
    // TODO - RoleCreateControllerRequestDto 대신에 RoleReadResponseDto 쓰기
    @Override
    @Transactional(readOnly = true)
    public Slice<RoleCreateControllerRequestDto> getAppliedRoles(Pageable pageable) {
        // TODO - token 통해 id 얻기
        Member member = memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );

        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMember(
                member,
                pageable
            );

        boolean hasNext = applicationRequestMemberSlice.hasNext();
        List<Role> roleList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getRole)
            .toList();

        // 확인용
        for (Role role : roleList){
            System.out.println(role.getRoleName());
        }

        // TODO - RoleEntityMapper에 toRoleReadResponseDtoList 정의되면 아래 걸로 수정
        // return new SliceImpl<>(roleEntityMapper.toRoleReadResponseDtoList(roleList), pageable, hasNext);
        return null;
    }

    // 출연자가 특정 상태(지원중, 미승인, 승인)에 따라 본인이 지원한 역할들 보려 할 때
    // TODO - RoleCreateControllerRequestDto 대신에 RoleReadResponseDto 쓰기
    @Override
    @Transactional(readOnly = true)
    public Slice<RoleCreateControllerRequestDto> getAppliedRolesByStatus(
        final ApplyStatus applyStatus,
        Pageable pageable
    ) {
        // TODO - token 통해 id 얻기
        Member member = memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        Slice<ApplicationRequestMember> applicationRequestMemberSlice =
            applicationRequestMemberRepository.findAllByMemberAndApplyStatus(
                member,
                applyStatus,
                pageable
            );
        boolean hasNext = applicationRequestMemberSlice.hasNext();
        List<Role> roleList = applicationRequestMemberSlice.stream()
            .map(ApplicationRequestMember::getRole)
            .toList();

        // 확인용
        for (Role role : roleList){
            System.out.println(role.getRoleName());
        }

        // TODO - RoleEntityMapper에 toRoleReadResponseDtoList 정의되면 아래 걸로 수정
        // return new SliceImpl<>(roleEntityMapper.toRoleReadResponseDtoList(roleList), pageable, hasNext);
        return null;
    }

    // 출연자가 지원 요청을 취소할 때
    // front 단에서는 승인 대기 상태로 보여지는데 실제로는 승인이 된 경우 있을 수 있어서(사용자가 새로고침 안한 경우)
    // 트랜잭션 걸어놓고 DB 상에서 applyStatus 검증해야 함.
    @Override
    public void deleteApplicationRequestMember(
        final ApplicationRequestDeleteServiceRequestDto applicationRequestDeleteServiceRequestDto
    ) {
        // TODO - token 통해 id 얻기
        Member member = memberRepository.findById(1L).orElseThrow(
            // TODO - member의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );
        Role role = roleRepository.findById(applicationRequestDeleteServiceRequestDto.roleId()).orElseThrow(
            // TODO - role의 NOT EXIST exception 으로 변경
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST)
        );

        Optional<ApplicationRequestMember> applicationRequestMemberFromDB =
            applicationRequestMemberRepository.findByMemberAndRole(
                member,
                role
            );
        // 지우려 했는데 없으면 예외 발생.
        applicationRequestMemberFromDB.orElseThrow(
            ()-> new NotFoundApplicationRequestMemberException(
                ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST_MEMBER
            )
        );

        // 승인 대기 상태도, 미승인 상태도 아닌 경우(승인 상태) 삭제 불가
        ApplyStatus applyStatus =  applicationRequestMemberFromDB.get().getApplyStatus();
        if (applyStatus != ApplyStatus.APPLIED && applyStatus != ApplyStatus.REJECTED){
            throw new NotAbleToCancelApplicationRequestMemberException(
                ApplicationRequestErrorCode.NOT_ABLE_TO_CANCEL_APPLICATION_REQUEST_MEMBER
            );
        }

        ApplicationRequestMember applicationRequestMember =
            applicationRequestEntityMapper.toApplicationRequestMemberFromDeleteRequest(
                applicationRequestDeleteServiceRequestDto,
                member,
                role
            );
        applicationRequestMemberRepository.delete(applicationRequestMember);
    }

}
