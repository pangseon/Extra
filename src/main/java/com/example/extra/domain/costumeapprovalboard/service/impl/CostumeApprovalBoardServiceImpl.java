package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CostumeApprovalBoardServiceImpl implements CostumeApprovalBoardService {
    private final CostumeApprovalBoardRepository costumeApprovalBoardRepository;
    @Override
    @Transactional(readOnly = true)
    public CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        final Company company,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if(!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(), company.getId()))
        {
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_READ_COSTUME_APPROVAL_BOARD);
        }
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto.from(costumeApprovalBoard);
    }
}
