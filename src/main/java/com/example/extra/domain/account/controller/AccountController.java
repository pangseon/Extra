package com.example.extra.domain.account.controller;

import com.example.extra.domain.account.dto.controller.AccountCreateControllerRequestDto;
import com.example.extra.domain.account.dto.controller.AccountLoginControllerRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountLoginServiceRequestDto;
import com.example.extra.domain.account.mapper.dto.AccountDtoMapper;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // mapper
    private final AccountDtoMapper accountDtoMapper;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/signup")
    public ResponseEntity<?> signup(AccountCreateControllerRequestDto controllerRequestDto) {
        AccountCreateServiceRequestDto serviceRequestDto =
            accountDtoMapper.toAccountCreateServiceRequestDto(controllerRequestDto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(accountService.signup(serviceRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(AccountLoginControllerRequestDto controllerRequestDto) {
        AccountLoginServiceRequestDto serviceRequestDto =
            accountDtoMapper.toAccountLoginServiceRequestDto(controllerRequestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(
                AUTHORIZATION_HEADER,
                accountService.login(serviceRequestDto).accessToken())
            .build();
    }
}
