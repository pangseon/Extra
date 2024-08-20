package com.example.extra.domain.company.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadServiceResponseDto;

public interface CompanyService {

    void signup(CompanyCreateServiceRequestDto companyCreateServiceRequestDto);

    CompanyReadServiceResponseDto readOnce(Account account);
}
