package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
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
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD_MEMBER)
            );
        return CostumeApprovalBoardMemberReadServiceResponseDto.from(costumeApprovalBoard);
    }
}
