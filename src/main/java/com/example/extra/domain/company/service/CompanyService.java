package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadServiceResponseDto;
import com.example.extra.global.security.UserDetailsImpl;

public interface CompanyService {

    void signup(
        final CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );

    CompanyReadServiceResponseDto readOnce(
        final UserDetailsImpl userDetails
    );
}
