package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;

public interface CostumeApprovalBoardService {
    CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        Member member,
        Long roleId
    );
    void deleteCostumeApprovalBoardByMember(
        Member member,
        Long roleId
    );
}
