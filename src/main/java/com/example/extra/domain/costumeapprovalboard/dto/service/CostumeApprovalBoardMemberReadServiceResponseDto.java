package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardMemberReadServiceResponseDto(
    String role_name,
    Boolean sex,
    String costume,
    String explain,
    String image_url,
    Boolean costume_approve
) {
    public static CostumeApprovalBoardMemberReadServiceResponseDto from(CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardMemberReadServiceResponseDto
            .builder()
                .role_name(costumeApprovalBoard.getRole().getRole_name())
                .sex(costumeApprovalBoard.getRole().getSex())
                .costume(costumeApprovalBoard.getRole().getCostume())
                .explain(costumeApprovalBoard.getImage_explain())
                .image_url(costumeApprovalBoard.getCostume_image_url())
                .costume_approve(costumeApprovalBoard.getCostume_approve())
            .build();
    }
}
