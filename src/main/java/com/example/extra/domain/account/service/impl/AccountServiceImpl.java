package com.example.extra.domain.account.service.impl;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;
import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.mapper.entity.AccountEntityMapper;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    // mapper
    private final AccountEntityMapper accountEntityMapper;

    @Override
    @Transactional
    public AccountCreateServiceResponseDto signup(
        final AccountCreateServiceRequestDto serviceRequestDto
    ) {
        Account account = accountEntityMapper.toAccount(serviceRequestDto);

        return new AccountCreateServiceResponseDto(accountRepository.save(account).getId());
    }
}
