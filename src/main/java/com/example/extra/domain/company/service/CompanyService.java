package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadOnceServiceResponseDto;
import com.example.extra.global.security.UserDetailsImpl;

public interface CompanyService {

    void signup(
        final CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );

    CompanyLoginServiceResponseDto login(
        final CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    );

    CompanyReadOnceServiceResponseDto readOnceCompany(
        final UserDetailsImpl userDetails
    );

    void logout(
        final UserDetailsImpl userDetails
    );
}