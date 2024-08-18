package com.example.extra.domain.account.service.impl;

import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountLoginServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;
import com.example.extra.domain.account.dto.service.response.AccountLoginServiceResponseDto;
import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.mapper.entity.AccountEntityMapper;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.account.service.AccountService;
import com.example.extra.domain.refreshtoken.repository.RefreshTokenRepository;
import com.example.extra.domain.refreshtoken.token.RefreshToken;
import com.example.extra.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    // mapper
    private final AccountEntityMapper accountEntityMapper;

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public AccountCreateServiceResponseDto signup(
        final AccountCreateServiceRequestDto serviceRequestDto
    ) {
        checkEmailDuplication(serviceRequestDto); // 이메일 중복 검사
        Account account = Account.builder()
            .email(serviceRequestDto.email())
            .password(passwordEncoder.encode(serviceRequestDto.password()))
            .userRole(serviceRequestDto.userRole())
            .folderUrl(serviceRequestDto.email())
            .build();

        return new AccountCreateServiceResponseDto(accountRepository.save(account).getId());
    }

    private void checkEmailDuplication(final AccountCreateServiceRequestDto serviceRequestDto) {
        accountRepository.findByEmail(serviceRequestDto.email())
            .ifPresent(a -> {
                throw new AccountException(AccountErrorCode.DUPLICATION_EMAIL);
            });
    }

    @Override
    @Transactional
    public AccountLoginServiceResponseDto login(
        final AccountLoginServiceRequestDto serviceRequestDto
    ) {
        Account account = accountRepository.findByEmail(serviceRequestDto.email())
            .orElseThrow(() -> new AccountException(AccountErrorCode.INVALID_EMAIL));

        if (!passwordEncoder.matches(
            serviceRequestDto.password(),
            account.getPassword()
        )) {
            throw new AccountException(AccountErrorCode.INVALID_PASSWORD);
        }

        // jwt 토큰 생성
        String accessToken = jwtUtil.createToken(
            account.getEmail(),
            account.getUserRole()
        );
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("access token: " + accessToken);
        log.info("refresh token: " + refreshToken);

        account.updateRefreshToken(jwtUtil.substringToken(refreshToken));

        refreshTokenRepository.save(
            new RefreshToken(
                account.getId().toString(),
                jwtUtil.substringToken(refreshToken)
            )
        );

        return new AccountLoginServiceResponseDto(accessToken, refreshToken);
    }
}
