package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadDetailServiceResponseDto(
    Long id,
    String memberName,
    String roleName,
    Boolean sex,
    String costume,
    String imageExplain,
    String imageUrl,
    ApplyStatus costumeApprove
) {

    public static CostumeApprovalBoardCompanyReadDetailServiceResponseDto from(
        CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto
            .builder()
            .id(costumeApprovalBoard.getId())
            .memberName(costumeApprovalBoard.getMember().getName())
            .roleName(costumeApprovalBoard.getRole().getRoleName())
            .sex(costumeApprovalBoard.getRole().getSex())
            .costume(costumeApprovalBoard.getRole().getCostume())
            .imageExplain(costumeApprovalBoard.getImageExplain())
            .costumeApprove(costumeApprovalBoard.getCostumeApprove())
            .build();
    }
}
