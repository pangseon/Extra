package com.example.extra.domain.company.dto.service.request;

public record CompanyCreateServiceRequestDto(
    Long accountId,
    String email,
    String password,
    String name
) {

}
