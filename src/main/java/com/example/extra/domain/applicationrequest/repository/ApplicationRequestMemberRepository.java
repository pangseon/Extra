package com.example.extra.domain.applicationrequest.repository;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.global.enums.ApplyStatus;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRequestMemberRepository extends JpaRepository<ApplicationRequestMember, Long> {
    Optional<ApplicationRequestMember> findByMemberIdAndRoleId(Long memberId, Long roleId);
    Slice<ApplicationRequestMember> findAllByMemberId(
        Long memberId,
        Pageable page
    );
    Slice<ApplicationRequestMember> findAllByMemberIdAndApplyStatus(
        Long memberId,
        ApplyStatus applyStatus,
        Pageable page
    );

    Slice<ApplicationRequestMember> findAllByRoleId(
        Long roleId,
        Pageable page
    );
    Slice<ApplicationRequestMember> findAllByRoleIdAndApplyStatus(
        Long roleId,
        ApplyStatus applyStatus,
        Pageable page
    );

}