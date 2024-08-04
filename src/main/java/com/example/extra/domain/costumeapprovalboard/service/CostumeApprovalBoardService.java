package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import java.util.List;

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

    void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Member member,
        final CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto
    );
}
