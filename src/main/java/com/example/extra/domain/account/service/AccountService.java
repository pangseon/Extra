package com.example.extra.domain.account.service;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;

public interface AccountService {

    AccountCreateServiceResponseDto signup(AccountCreateServiceRequestDto serviceRequestDto);
}
