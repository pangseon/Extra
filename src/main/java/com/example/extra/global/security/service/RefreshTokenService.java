package com.example.extra.global.security.service;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.global.security.JwtUtil;
import com.example.extra.global.security.exception.TokenErrorCode;
import com.example.extra.global.security.exception.TokenException;
import com.example.extra.global.security.repository.RefreshTokenRepository;
import com.example.extra.global.security.token.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
        final Authentication authentication,
        final HttpServletRequest httpServletRequest,
        final HttpServletResponse httpServletResponse
    ) {
        String accessToken = httpServletRequest.getHeader("Authorization");
        log.info(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken)
            .orElseThrow(() -> new TokenException(TokenErrorCode.NOT_FOUND_TOKEN));

        log.info(refreshToken.toString());
        // refresh token 만료 확인
        if (jwtUtil.isExpired(refreshToken.getRefreshToken()) &&
            refreshToken.getRefreshToken() != null) {
            log.error("Expired JWT refresh token : 만료된 JWT refresh token");
        } else {
            Member member = memberRepository.findById(refreshToken.getId())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
            // accessToken 재발급 & 저장
            String newAccessToken = jwtUtil.createToken(member.getEmail(), member.getUserRole());
            String newRefreshToken = jwtUtil.createRefreshToken();

            refreshTokenRepository.delete(refreshToken);
            RefreshToken save = refreshTokenRepository.save(
                new RefreshToken(
                    member.getId(),
                    newRefreshToken,
                    newAccessToken)
            );

            jwtUtil.addTokenHeader(save.getAccessToken(), httpServletResponse);
            log.info(httpServletResponse.getHeader("Authorization"));
        }
    }
}