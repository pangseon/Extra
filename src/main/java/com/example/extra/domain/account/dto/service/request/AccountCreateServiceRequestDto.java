package com.example.extra.domain.account.dto.service.request;

import com.example.extra.global.enums.UserRole;

public record AccountCreateServiceRequestDto(
    String email,
    String password,
    UserRole userRole
) {

}
