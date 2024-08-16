package com.example.extra.domain.costumeapprovalboard.dto.service;

import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardCompanyReadServiceResponseDto(
    Long id,
    String memberName,
    String imageUrl,
    ApplyStatus costumeApprove
) {

    public static CostumeApprovalBoardCompanyReadServiceResponseDto from(
        CostumeApprovalBoard costumeApprovalBoard) {
        return CostumeApprovalBoardCompanyReadServiceResponseDto
            .builder()
                .id(costumeApprovalBoard.getId())
                .memberName(costumeApprovalBoard.getMember().getName())
                .imageUrl(costumeApprovalBoard.getMember().getAccount().getImageUrl())
                .costumeApprove(costumeApprovalBoard.getCostumeApprove())
            .build();
    }
}
