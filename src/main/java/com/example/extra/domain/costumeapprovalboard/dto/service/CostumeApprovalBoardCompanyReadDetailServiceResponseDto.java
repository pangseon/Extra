package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadDetailServiceResponseDto(
    String member_name,
    String role_name,
    Boolean sex,
    String costume,
    String image_explain,
    String image_url,
    Boolean costume_approve
) {
    public static CostumeApprovalBoardCompanyReadDetailServiceResponseDto from(CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto
            .builder()
                .member_name(costumeApprovalBoard.getMember().getName())
                .role_name(costumeApprovalBoard.getRole().getRole_name())
                .sex(costumeApprovalBoard.getRole().getSex())
                .costume(costumeApprovalBoard.getRole().getCostume())
                .image_explain(costumeApprovalBoard.getImage_explain())
                .costume_approve(costumeApprovalBoard.getCostume_approve())
            .build();
    }
}
