package com.example.extra.domain.company.service;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;

public interface CompanyService {

    void signup(
        CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    );

    CompanyLoginServiceResponseDto login(
        CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    );
}
