package com.example.extra.global.security.service;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.global.security.JwtUtil;
import com.example.extra.global.security.UserDetailsImpl;
import com.example.extra.global.security.exception.TokenErrorCode;
import com.example.extra.global.security.exception.TokenException;
import com.example.extra.global.security.repository.RefreshTokenRepository;
import com.example.extra.global.security.token.RefreshToken;
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
    private final MemberRepository memberRepository;

    public void getNewAccessToken(
        final UserDetailsImpl userDetails,
        final HttpServletRequest httpServletRequest,
        final HttpServletResponse httpServletResponse
    ) {
        String token = httpServletRequest.getHeader("Authorization");
        log.info(token);
        Member member = memberRepository.findByRefreshToken(jwtUtil.substringToken(token))
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));

        RefreshToken refreshToken = refreshTokenRepository.findById(member.tokenId())
            .orElseThrow(() -> new TokenException(TokenErrorCode.NOT_FOUND_TOKEN));

        // redis refresh token == rdb refresh token
        if (refreshToken.getRefreshToken().equals(member.getRefreshToken())) {
            String newAccessToken = jwtUtil.createToken(member.getEmail(), member.getUserRole());
            String newRefreshToken = jwtUtil.createRefreshToken();

            refreshTokenRepository.delete(refreshToken);
            refreshTokenRepository.save(
                new RefreshToken(
                    member.tokenId(),
                    jwtUtil.substringToken(newRefreshToken)
                )
            );

            member.updateRefreshToken(jwtUtil.substringToken(newRefreshToken));
            jwtUtil.addTokenHeader(newAccessToken, httpServletResponse);
        } else {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }

    }
}