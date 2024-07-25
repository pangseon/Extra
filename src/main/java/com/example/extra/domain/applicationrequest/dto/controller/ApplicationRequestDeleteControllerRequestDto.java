package com.example.extra.domain.applicationrequest.dto.controller;

import jakarta.validation.constraints.NotNull;

// controller 단에서 request body(API 입력) 받는 용도
public record ApplicationRequestDeleteControllerRequestDto(
    @NotNull
    Long roleId
) {

}