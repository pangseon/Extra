package com.example.extra.domain.account.dto.controller;

public record AccountCreateControllerRequestDto(
    String email,
    String password,
    String userRole
) {

}
