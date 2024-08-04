package com.example.extra.domain.costumeapprovalboard.repository;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import com.example.extra.domain.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostumeApprovalBoardRepository extends JpaRepository<CostumeApprovalBoard, Long> {
    Optional<List<CostumeApprovalBoard>> findAllByRole(Role role);
    Optional<CostumeApprovalBoard> findByMemberAndRole(
        Member member,
        Role role
    );
}
