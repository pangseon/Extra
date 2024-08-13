package com.example.extra.domain.account.service.impl;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;
import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.mapper.entity.AccountEntityMapper;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    // mapper
    private final AccountEntityMapper accountEntityMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AccountCreateServiceResponseDto signup(
        final AccountCreateServiceRequestDto serviceRequestDto
    ) {
        checkEmailDuplication(serviceRequestDto); // 이메일 중복 검사
        Account account = accountEntityMapper.toAccount(serviceRequestDto);
        account.encodePassword(passwordEncoder.encode(account.getPassword())); // 패스워드 인코딩

        return new AccountCreateServiceResponseDto(accountRepository.save(account).getId());
    }

    private void checkEmailDuplication(final AccountCreateServiceRequestDto serviceRequestDto) {
        accountRepository.findByEmail(serviceRequestDto.email())
            .ifPresent(a -> {
                throw new AccountException(AccountErrorCode.DUPLICATION_EMAIL);
            });
    }
}
