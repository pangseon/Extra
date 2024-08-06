package com.example.extra.domain.applicationrequest.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequestMemberUpdateControllerRequestDto(
    @NotNull(message = "유효하지 않은 apply status입니다.")
    ApplyStatus applyStatus
) {

}
