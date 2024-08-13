package com.example.extra.domain.account.dto.service.request;

public record AccountCreateServiceRequestDto(
    String email,
    String password,
    String userRole
) {

}
