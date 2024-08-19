package com.example.extra.domain.company.mapper.entity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadServiceResponseDto;
import com.example.extra.domain.company.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CompanyEntityMapper {

    Company toCompany(
        CompanyCreateServiceRequestDto companyCreateServiceRequestDto,
        Account account
    );

    CompanyReadServiceResponseDto toCompanyReadOnceServiceResponseDto(
        Company company
    );
}
