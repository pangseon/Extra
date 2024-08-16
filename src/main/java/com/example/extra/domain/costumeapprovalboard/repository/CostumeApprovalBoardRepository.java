package com.example.extra.domain.costumeapprovalboard.repository;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumeApprovalBoardRepository extends JpaRepository<CostumeApprovalBoard, Long> {
    Slice<CostumeApprovalBoard> findAllByRole(
        Role role,
        Pageable pageable
    );
    Slice<CostumeApprovalBoard> findAllByRoleAndMember_NameContaining(
        Role role,
        String memberName,
        Pageable pageable
    );
    Optional<CostumeApprovalBoard> findByMemberAndRoleId(
        Member member,
        Long roleId
    );
    Optional<CostumeApprovalBoard> findByMemberAndRole(
        Member member,
        Role role
    );
    Optional<CostumeApprovalBoard> findByIdAndMember(Long id,Member member);
}
