package com.example.extra.domain.costumeapprovalboard.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardApplyStatusUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface CostumeApprovalBoardService {

    List<CostumeApprovalBoardCompanyReadServiceResponseDto> getCostumeApprovalBoardForCompany(
        final Account account,
        final Long jobPostId
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
        final CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto,
        final MultipartFile multipartFile
    ) throws IOException;

    void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Account account,
        final CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto,
        final MultipartFile multipartFile
    )throws IOException;

    void updateCostumeApprovalBoardByCompany(
        final Account account,
        final Long costumeApprovalBoardId,
        final CostumeApprovalBoardApplyStatusUpdateServiceRequestDto controllerRequestDto
    );
}
