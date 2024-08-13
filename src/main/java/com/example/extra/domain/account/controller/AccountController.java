package com.example.extra.domain.account.controller;

import com.example.extra.domain.account.dto.controller.AccountCreateControllerRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.mapper.dto.AccountDtoMapper;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // mapper
    private final AccountDtoMapper accountDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(AccountCreateControllerRequestDto controllerRequestDto) {
        AccountCreateServiceRequestDto serviceRequestDto =
            accountDtoMapper.toAccountCreateServiceRequestDto(controllerRequestDto);

        accountService.signup(serviceRequestDto);
        
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }

}
