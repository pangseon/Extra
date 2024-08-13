package com.example.extra.domain.account.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.account.dto.controller.AccountCreateControllerRequestDto;
import com.example.extra.domain.account.dto.controller.AccountLoginControllerRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountLoginServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface AccountDtoMapper {

    AccountCreateServiceRequestDto toAccountCreateServiceRequestDto(
        AccountCreateControllerRequestDto controllerRequestDto
    );

    AccountLoginServiceRequestDto toAccountLoginServiceRequestDto(
        AccountLoginControllerRequestDto controllerRequestDto
    );
}
