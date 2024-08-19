package com.example.extra.domain.costumeapprovalboard.dto.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CostumeApprovalBoardExplainUpdateControllerRequestDto(
    @Size(max = 255, message = "imageExplain은 최대 255자입니다.")
    String imageExplain,
    @NotNull(message = "imageChange는 필수 정보 입니다.")
    Boolean imageChange
) {

}
