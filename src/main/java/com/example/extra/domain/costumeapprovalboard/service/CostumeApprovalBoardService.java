package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;

public interface CostumeApprovalBoardService {
    CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        Company company,
        Long costumeApprovalBoardId
    );
}
