package com.example.extra.domain.costumeapprovalboard.dto.service.request;

public record CostumeApprovalBoardUpdateServiceRequestDto(
    String imageExplain,
    boolean isImageUpdate,
    String imageUrl
) {

}
