package com.example.extra.domain.applicationrequest.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequestMemberUpdateControllerRequestDto(
    @NotNull(message = "유효한 apply status를 입력해주세요.")
    ApplyStatus applyStatus
) {

}
