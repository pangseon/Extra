package com.example.extra.domain.applicationrequest.repository;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.enums.ApplyStatus;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationRequestMemberRepository extends
    JpaRepository<ApplicationRequestMember, Long> {

    Slice<ApplicationRequestMember> findAllByMember(
        Member member,
        Pageable page
    );
    List<ApplicationRequestMember> findAllByMember(
        Member member
    );

    Slice<ApplicationRequestMember> findAllByMemberAndApplyStatus(
        Member member,
        ApplyStatus applyStatus,
        Pageable page
    );

    Slice<ApplicationRequestMember> findAllByRole(
        Role role,
        Pageable page
    );

    List<ApplicationRequestMember> findAllByRoleAndApplyStatus(
        Role role,
        ApplyStatus applyStatus
    );

    List<ApplicationRequestMember> findAllByRoleAndApplyStatusAndMember_NameContaining(
        Role role,
        ApplyStatus applyStatus,
        String name
    );

    Optional<ApplicationRequestMember> findByMemberAndRole(
        Member member,
        Role role
    );
}