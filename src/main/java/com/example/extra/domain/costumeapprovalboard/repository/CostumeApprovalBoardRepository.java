package com.example.extra.domain.costumeapprovalboard.repository;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumeApprovalBoardRepository extends JpaRepository<CostumeApprovalBoard, Long> {
    Optional<List<CostumeApprovalBoard>> findAllByRole(Role role);
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
