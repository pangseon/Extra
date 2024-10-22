package com.example.extra.domain.applicationrequest.dto.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ApplicationRequestMemberUpdateControllerRequestDto(
    @NotNull(message = "applyStatus는 필수 정보입니다")
    @Pattern(
        regexp = "^(APPLIED|REJECTED|APPROVED)$",
        message = "APPLIED, REJECTED, APPROVED 중 하나를 입력해주세요"
    )
    String applyStatus
) {

}
