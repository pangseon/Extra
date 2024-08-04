package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;

public interface CostumeApprovalBoardService {
    CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        Member member,
        Long roleId
    );
    void deleteCostumeApprovalBoardByMember(
        Member member,
        Long costumeApprovalBoardId
    );
    void deleteCostumeApprovalBoardByCompany(
        Company company,
        Long costumeApprovalBoardId
    );
    void createCostumeApprovalBoard(
        final Long roleId,
        final Member member,
        final CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto
    );
}