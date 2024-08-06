package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardMemberReadServiceResponseDto(
    Long id,
    String role_name,
    Boolean sex,
    String costume,
    String image_explain,
    String image_url,
    ApplyStatus costume_approve
) {

    public static CostumeApprovalBoardMemberReadServiceResponseDto from(
        CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardMemberReadServiceResponseDto
            .builder()
            .id(costumeApprovalBoard.getId())
            .role_name(costumeApprovalBoard.getRole().getRole_name())
            .sex(costumeApprovalBoard.getRole().getSex())
            .costume(costumeApprovalBoard.getRole().getCostume())
            .image_explain(costumeApprovalBoard.getImage_explain())
            .image_url(costumeApprovalBoard.getCostume_image_url())
            .costume_approve(costumeApprovalBoard.getCostume_approve())
            .build();
    }
}