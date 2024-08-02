package com.example.extra.domain.applicationrequest.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequestMemberUpdateControllerRequestDto(
    @NotNull
    ApplyStatus applyStatus
) {

}
