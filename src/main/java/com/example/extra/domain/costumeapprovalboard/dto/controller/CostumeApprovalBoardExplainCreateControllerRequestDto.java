package com.example.extra.domain.costumeapprovalboard.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CostumeApprovalBoardExplainCreateControllerRequestDto(
    @Schema(description = "의상 이미지에 대한 설명. 구현 안됐으면 널값 넣으시면 됩니다.")
    @Size(max = 255, message = "imageExplain은 최대 255자입니다.")
    String imageExplain
) {

}
