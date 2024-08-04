package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.member.entity.Member;

public interface CostumeApprovalBoardService {

    void createCostumeApprovalBoard(
        final Long roleId,
        final Member member,
        final CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto
    );

    void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Member member,
        final CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto
    );
}
