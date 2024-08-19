package com.example.extra.domain.costumeapprovalboard.dto.controller;

import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardExplainCreateRequestDto(
    @Size(max = 255, message = "imageExplain은 최대 255자입니다.")
    String imageExplain
) {

}
