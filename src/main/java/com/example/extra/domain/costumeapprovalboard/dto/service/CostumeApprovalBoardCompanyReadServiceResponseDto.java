package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadServiceResponseDto(
    Long id,
    String member_name,
    String role_name,
    Boolean costume_approve
) {
    public static CostumeApprovalBoardCompanyReadServiceResponseDto from(CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadServiceResponseDto
            .builder()
                .id(costumeApprovalBoard.getId())
                .member_name(costumeApprovalBoard.getMember().getName())
                .role_name(costumeApprovalBoard.getRole().getRole_name())
                .costume_approve(costumeApprovalBoard.getCostume_approve())
            .build();
    }
}
