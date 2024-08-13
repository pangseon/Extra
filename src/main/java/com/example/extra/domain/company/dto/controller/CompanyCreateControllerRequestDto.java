package com.example.extra.domain.company.dto.controller;

import jakarta.validation.constraints.NotNull;

public record CompanyCreateControllerRequestDto(
    @NotNull
    Long accountId,
    @NotNull
    String name
) {

}
