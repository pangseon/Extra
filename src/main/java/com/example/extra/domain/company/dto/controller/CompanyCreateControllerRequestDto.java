package com.example.extra.domain.company.dto.controller;

public record CompanyCreateControllerRequestDto(
    String email,
    String password,
    String name
) {

}
