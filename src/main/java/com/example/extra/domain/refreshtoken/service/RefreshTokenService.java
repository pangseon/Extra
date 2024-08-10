package com.example.extra.domain.refreshtoken.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    public void getMemberNewAccessToken(
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

    public void getCompanyNewAccessToken(
        final HttpServletRequest httpServletRequest,
        final HttpServletResponse httpServletResponse
    ) {
        String token = httpServletRequest.getHeader("Authorization");
        log.info(token);
        Company company = companyRepository.findByRefreshToken(jwtUtil.substringToken(token))
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_COMPANY));

        RefreshToken refreshToken = refreshTokenRepository.findById(company.tokenId())
            .orElseThrow(() -> new TokenException(TokenErrorCode.NOT_FOUND_TOKEN));

        // redis refresh token == rdb refresh token
        if (refreshToken.getRefreshToken().equals(company.getRefreshToken())) {
            String newAccessToken = jwtUtil.createToken(company.getEmail(), company.getUserRole());
            String newRefreshToken = jwtUtil.createRefreshToken();

            refreshTokenRepository.delete(refreshToken);
            refreshTokenRepository.save(
                new RefreshToken(
                    company.tokenId(),
                    jwtUtil.substringToken(newRefreshToken)
                )
            );

            company.updateRefreshToken(jwtUtil.substringToken(newRefreshToken));
            jwtUtil.addTokenHeader(newAccessToken, httpServletResponse);
        } else {
            throw new TokenException(TokenErrorCode.INVALID_TOKEN);
        }
    }
}