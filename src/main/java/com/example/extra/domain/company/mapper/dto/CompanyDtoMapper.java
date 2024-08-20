package com.example.extra.domain.company.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.company.dto.controller.CompanyCreateControllerRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CompanyDtoMapper {

    CompanyCreateServiceRequestDto toCompanyCreateServiceRequestDto(
        CompanyCreateControllerRequestDto companyCreateControllerRequestDto
    );
}
