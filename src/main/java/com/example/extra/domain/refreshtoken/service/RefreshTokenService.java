package com.example.extra.domain.refreshtoken.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.refreshtoken.exception.TokenErrorCode;
import com.example.extra.domain.refreshtoken.exception.TokenException;
import com.example.extra.domain.refreshtoken.repository.RefreshTokenRepository;
import com.example.extra.domain.refreshtoken.token.RefreshToken;
import com.example.extra.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RefreshTokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;

    public void getNewAccessToken(
        final HttpServletRequest httpServletRequest,
        final HttpServletResponse httpServletResponse
    ) {
        String token = httpServletRequest.getHeader("Authorization");
        log.info(token);
        Account account = accountRepository.findByRefreshToken(jwtUtil.substringToken(token))
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));

        RefreshToken refreshToken = refreshTokenRepository.findById(account.getId().toString())
            .orElseThrow(() -> new TokenException(TokenErrorCode.NOT_FOUND_TOKEN));

        // redis refresh token == rdb refresh token
        if (refreshToken.getRefreshToken().equals(account.getRefreshToken())) {
            String newAccessToken = jwtUtil.createToken(account.getEmail(), account.getUserRole());
            String newRefreshToken = jwtUtil.createRefreshToken();

            refreshTokenRepository.delete(refreshToken);
            refreshTokenRepository.save(
                new RefreshToken(
                    account.getId().toString(),
                    jwtUtil.substringToken(newRefreshToken)
                )
            );

            account.updateRefreshToken(jwtUtil.substringToken(newRefreshToken));
            jwtUtil.addTokenHeader(newAccessToken, httpServletResponse);
        } else {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }
    }
}