package com.example.extra.domain.company.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CompanyCreateControllerRequestDto(
    @Schema(description = "계정 id")
    @NotBlank(message = "계정 id 값은 필수 입력 정보입니다")
    Long accountId,
    @Schema(description = "업체 이름", example = "엑스트라")
    @NotBlank(message = "업체 이름은 필수 입력 정보입니다")
    String name
) {

}
