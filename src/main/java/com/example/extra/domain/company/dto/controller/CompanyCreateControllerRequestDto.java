package com.example.extra.domain.company.dto.controller;

import jakarta.validation.constraints.NotNull;

public record CompanyCreateControllerRequestDto(
    @NotNull
    String email,
    @NotNull
    String password,
    @NotNull
    String name,
    boolean idAdmin,
    String adminToken
) {

}
