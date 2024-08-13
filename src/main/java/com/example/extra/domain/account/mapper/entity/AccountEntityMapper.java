package com.example.extra.domain.account.mapper.entity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface AccountEntityMapper {

    Account toAccount(
        AccountCreateServiceRequestDto serviceRequestDto
    );
}
