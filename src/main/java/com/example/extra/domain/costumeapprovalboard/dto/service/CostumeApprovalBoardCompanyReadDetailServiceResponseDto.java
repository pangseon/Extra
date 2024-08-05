package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadDetailServiceResponseDto(
    Long id,
    String member_name,
    String role_name,
    Boolean sex,
    String costume,
    String image_explain,
    String image_url,
    ApplyStatus costume_approve
) {

    public static CostumeApprovalBoardCompanyReadDetailServiceResponseDto from(
        CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto
            .builder()
            .id(costumeApprovalBoard.getId())
            .member_name(costumeApprovalBoard.getMember().getName())
            .role_name(costumeApprovalBoard.getRole().getRole_name())
            .sex(costumeApprovalBoard.getRole().getSex())
            .costume(costumeApprovalBoard.getRole().getCostume())
            .image_explain(costumeApprovalBoard.getImage_explain())
            .costume_approve(costumeApprovalBoard.getCostume_approve())
            .build();
    }
}
