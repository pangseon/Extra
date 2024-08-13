package com.example.extra.domain.account.controller;

import com.example.extra.domain.account.mapper.dto.AccountDtoMapper;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // mapper
    private final AccountDtoMapper accountDtoMapper;
}
