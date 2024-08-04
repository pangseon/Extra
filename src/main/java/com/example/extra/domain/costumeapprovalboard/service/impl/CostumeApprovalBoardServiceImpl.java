package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CostumeApprovalBoardServiceImpl implements CostumeApprovalBoardService {
    private final CostumeApprovalBoardRepository costumeApprovalBoardRepository;
    @Transactional(readOnly = true)
    public CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        Member member,
        Long roleId
    ){
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findByMemberAndRoleId(member, roleId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        return CostumeApprovalBoardMemberReadServiceResponseDto.from(costumeApprovalBoard);
    }
    @Transactional
    public void deleteCostumeApprovalBoardByMember(
        Member member,
        Long costumeApprovalBoardId
    ){
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if(!Objects.equals(costumeApprovalBoard.getMember().getId(), member.getId()))
        {
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }

    @Override
    public void deleteCostumeApprovalBoardByCompany(
        final Company company,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if(!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(), company.getId()))
        {
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }
}
