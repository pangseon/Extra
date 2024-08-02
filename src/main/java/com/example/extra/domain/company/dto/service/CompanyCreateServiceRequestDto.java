package com.example.extra.domain.company.dto.service;

public record CompanyCreateServiceRequestDto(
    String email,
    String password,
    String name
) {

}
