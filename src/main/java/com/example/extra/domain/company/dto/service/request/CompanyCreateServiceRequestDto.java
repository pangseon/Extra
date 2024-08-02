package com.example.extra.domain.company.dto.service.request;

public record CompanyCreateServiceRequestDto(
    String email,
    String password,
    String name,
    boolean isAdmin,
    String adminToken
) {

}
