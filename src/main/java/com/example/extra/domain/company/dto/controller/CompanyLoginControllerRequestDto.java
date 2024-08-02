package com.example.extra.domain.company.dto.controller;

import jakarta.validation.constraints.NotNull;

public record CompanyLoginControllerRequestDto(
    @NotNull
    String email,
    @NotNull
    String password
) {

}
