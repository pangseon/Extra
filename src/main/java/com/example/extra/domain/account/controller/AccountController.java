package com.example.extra.domain.account.controller;

import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

}
