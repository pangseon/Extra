package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.CompanyCreateServiceRequestDto;

public interface CompanyService {

    void signup(
        CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );
}
