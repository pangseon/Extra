package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import java.util.List;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;

public interface CostumeApprovalBoardService {
    List<CostumeApprovalBoardCompanyReadServiceResponseDto> getCostumeApprovalBoardForCompany(
        final Company company,
        final Long jobPostId
    );

    CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        final Company company,
        final Long costumeApprovalBoardId
    );

    CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        final Member member,
        final Long roleId
    );
    void deleteCostumeApprovalBoardByMember(
        final Member member,
        final Long costumeApprovalBoardId
    );
    void deleteCostumeApprovalBoardByCompany(
        final Company company,
        final Long costumeApprovalBoardId
    );
    void createCostumeApprovalBoard(
        final Long roleId,
        final Member member,
        final CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto
    );

}