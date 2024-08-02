package com.example.extra.domain.company.service.impl;

import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.mapper.entity.CompanyEntityMapper;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.company.service.CompanyService;
import com.example.extra.global.security.JwtUtil;
import com.example.extra.global.security.repository.RefreshTokenRepository;
import com.example.extra.global.security.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CompanyEntityMapper companyEntityMapper;

    // security
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "admintoken";

    @Override
    @Transactional
    public void signup(
        final CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    ) {
        // 이메일 중복 검사
        String email = companyCreateServiceRequestDto.email();
        companyRepository.findByEmail(email)
            .ifPresent(c -> {
                throw new CompanyException(CompanyErrorCode.ALREADY_EXIST_EMAIL);
            });

        Company company = companyEntityMapper.toCompany(companyCreateServiceRequestDto);
        company.encodePassword(passwordEncoder.encode(company.getPassword()));
        companyRepository.save(company);

        // role 변경 (ROLE_USER -> ROLE_ADMIN)
        if (companyCreateServiceRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(companyCreateServiceRequestDto.adminToken())) {
                throw new IllegalArgumentException("관리자 암호 아님");
            }
            company.updateRole();
            companyRepository.save(company);
        }
    }

    @Override
    @Transactional
    public CompanyLoginServiceResponseDto login(
        final CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    ) {
        Company company = companyRepository.findByEmail(companyLoginServiceRequestDto.email())
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_COMPANY));

        if (!passwordEncoder.matches(
            companyLoginServiceRequestDto.password(),
            company.getPassword()
        )) {
            throw new CompanyException(CompanyErrorCode.INVALID_PASSWORD);
        }

        // jwt 토큰 생성
        String accessToken = jwtUtil.createToken(
            company.getEmail(),
            company.getUserRole()
        );
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("access token: " + accessToken);
        log.info("refresh token: " + refreshToken);

        company.updateRefreshToken(jwtUtil.substringToken(refreshToken));

        refreshTokenRepository.save(
            new RefreshToken(
                company.getId(),
                jwtUtil.substringToken(refreshToken)
            )
        );

        return new CompanyLoginServiceResponseDto(accessToken);
    }
}
