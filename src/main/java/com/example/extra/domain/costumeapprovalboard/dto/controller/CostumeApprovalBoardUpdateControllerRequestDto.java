package com.example.extra.domain.costumeapprovalboard.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CostumeApprovalBoardUpdateControllerRequestDto(
    @Schema(description = "의상 이미지에 대한 설명. 구현 안됐으면 널값 넣으시면 됩니다.")
    @Size(max = 255, message = "imageExplain은 최대 255자입니다.")
    String imageExplain,
    @Schema(description = "의상 이미지 수정 여부", defaultValue = "false")
    @NotNull(message = "isImageUpdate는 필수 입력 정보입니다")
    boolean isImageUpdate,
    @Schema(description = "수정할 기존 이미지 url")
    String imageUrl
) {

}
