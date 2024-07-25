package com.example.extra.domain.applicationrequest.dto.controller;

import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.constraints.NotNull;

// controller 단에서 request body(API 입력) 받는 용도
public record ApplicationRequestCreateControllerRequestDto(
    @NotNull
    ApplyStatus applyStatus,
    @NotNull
    Long roleId
) {

}