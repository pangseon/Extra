package com.example.extra.domain.company.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CompanyCreateControllerRequestDto(
    @Schema(description = "계정 id")
    @NotNull(message = "계정 id 값은 필수 입력 정보입니다")
    Long accountId,
    @Schema(description = "업체 이름", example = "엑스트라")
    @NotBlank(message = "업체 이름은 필수 입력 정보입니다")
    @Size(max = 30)
    String name
) {

}
