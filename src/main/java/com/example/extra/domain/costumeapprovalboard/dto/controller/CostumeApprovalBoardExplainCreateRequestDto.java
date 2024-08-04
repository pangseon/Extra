package com.example.extra.domain.costumeapprovalboard.dto.controller;

import lombok.Builder;

@Builder
public record CostumeApprovalBoardExplainCreateRequestDto(
    String image_explain
) {

}
