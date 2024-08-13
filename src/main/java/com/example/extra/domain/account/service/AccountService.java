package com.example.extra.domain.account.service;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountLoginServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;
import com.example.extra.domain.account.dto.service.response.AccountLoginServiceResponseDto;

public interface AccountService {

    AccountCreateServiceResponseDto signup(AccountCreateServiceRequestDto serviceRequestDto);

    AccountLoginServiceResponseDto login(AccountLoginServiceRequestDto serviceRequestDto);
}
