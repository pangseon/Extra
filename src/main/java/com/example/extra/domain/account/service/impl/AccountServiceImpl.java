package com.example.extra.domain.account.service.impl;

import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

}
