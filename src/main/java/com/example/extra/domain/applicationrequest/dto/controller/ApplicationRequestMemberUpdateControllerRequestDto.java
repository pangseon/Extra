package com.example.extra.domain.applicationrequest.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequestMemberUpdateControllerRequestDto(
    @NotNull(message = "유효한 applyStatus 값을 입력해주세요. 가능한 값: 'APPLIED', 'REJECTED', 'APPROVED'")
    ApplyStatus applyStatus
) {

}
