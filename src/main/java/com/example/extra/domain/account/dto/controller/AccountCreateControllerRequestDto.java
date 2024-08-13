package com.example.extra.domain.account.dto.controller;

import com.example.extra.global.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record AccountCreateControllerRequestDto(
    @NotNull
    String email,
    @NotNull
    String password,
    @NotNull
    UserRole userRole
) {

}
