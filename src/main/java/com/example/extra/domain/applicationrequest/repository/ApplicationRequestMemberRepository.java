package com.example.extra.domain.applicationrequest.repository;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.enums.ApplyStatus;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRequestMemberRepository extends JpaRepository<ApplicationRequestMember, Long> {
    Optional<ApplicationRequestMember> findByMemberAndRole(
        Member member,
        Role role
    );
    Slice<ApplicationRequestMember> findAllByMember(
        Member member,
        Pageable page
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
    Slice<ApplicationRequestMember> findAllByRoleAndApplyStatus(
        Role role,
        ApplyStatus applyStatus,
        Pageable page
    );

}