package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardApplyStatusUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardExplainCreateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardMemberReadServiceResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CostumeApprovalBoardService {

    List<CostumeApprovalBoardCompanyReadServiceResponseDto> getCostumeApprovalBoardForCompany(
        final Account account,
        final Long roleId,
        final String name,
        final Pageable pageable
    );

    CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        final Account account,
        final Long costumeApprovalBoardId
    );

    CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        final Account account,
        final Long roleId
    );

    void deleteCostumeApprovalBoardByMember(
        final Account account,
        final Long costumeApprovalBoardId
    );

    void deleteCostumeApprovalBoardByCompany(
        final Account account,
        final Long costumeApprovalBoardId
    );

    void createCostumeApprovalBoard(
        final Long roleId,
        final Account account,
        final CostumeApprovalBoardExplainCreateServiceRequestDto costumeApprovalBoardExplainCreateServiceRequestDto,
        final MultipartFile multipartFile
    );

    void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Account account,
        final CostumeApprovalBoardUpdateServiceRequestDto serviceRequestDto,
        final MultipartFile multipartFile
    );

    void updateCostumeApprovalBoardByCompany(
        final Account account,
        final Long costumeApprovalBoardId,
        final CostumeApprovalBoardApplyStatusUpdateServiceRequestDto controllerRequestDto
    );
}
