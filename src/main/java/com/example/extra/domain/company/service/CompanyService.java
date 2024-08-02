package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;

public interface CompanyService {

    void signup(
        final CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );

    CompanyLoginServiceResponseDto login(
        final CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    );
    );
}
