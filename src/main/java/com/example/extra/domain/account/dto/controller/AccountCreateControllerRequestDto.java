package com.example.extra.domain.account.dto.controller;

import com.example.extra.global.enums.UserRole;

public record AccountCreateControllerRequestDto(
    String email,
    String password,
    UserRole userRole
) {

}
