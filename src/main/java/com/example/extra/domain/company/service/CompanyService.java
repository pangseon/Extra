package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadOnceServiceResponseDto;
import com.example.extra.domain.company.entity.Company;

public interface CompanyService {

    void signup(
        final CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );

    CompanyLoginServiceResponseDto login(
        final CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    );

    CompanyReadOnceServiceResponseDto readOnceCompany(
        final Company company
    );

    void logout(
        final Company company
    );
}
