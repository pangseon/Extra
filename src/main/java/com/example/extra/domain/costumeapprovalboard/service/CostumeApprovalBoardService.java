package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;

public interface CostumeApprovalBoardService {
    CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        Member member,
        Long roleId
    );
    void createCostumeApprovalBoard(
        final Long roleId,
        final Member member,
        final CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto
    );
}