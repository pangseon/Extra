package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadServiceResponseDto(
    Long id,
    String member_name,
    String imageUrl,
    ApplyStatus costume_approve
) {

    public static CostumeApprovalBoardCompanyReadServiceResponseDto from(
        CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadServiceResponseDto
            .builder()
                .id(costumeApprovalBoard.getId())
                .member_name(costumeApprovalBoard.getMember().getName())
                .imageUrl(costumeApprovalBoard.getMember().getAccount().getImageUrl())
                .costume_approve(costumeApprovalBoard.getCostumeApprove())
            .build();
    }
}
