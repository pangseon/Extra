package com.example.extra.domain.company.dto.controller;

import jakarta.validation.constraints.NotNull;

public record CompanyCreateControllerRequestDto(
    @NotNull
    Long account_id,
    @NotNull
    String email,
    @NotNull
    String password,
    @NotNull
    String name
) {

}
