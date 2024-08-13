package com.example.extra.domain.company.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadOnceServiceResponseDto;
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
    private final AccountRepository accountRepository;

    // mapper
    private final CompanyEntityMapper companyEntityMapper;

    // security
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String ADMIN_TOKEN = "admintoken";

    @Override
    @Transactional
    public void signup(
        final CompanyCreateServiceRequestDto serviceRequestDto
    ) {
        Account account = accountRepository.findById(serviceRequestDto.accountId())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));

        // Account 권한이 업체가 아닌 경우 -> throw error
        if (!account.getUserRole().getAuthority().equals("ROLE_COMPANY")) {
            throw new AccountException(AccountErrorCode.INVALID_ROLE_COMPANY);
        }

        Company company = companyEntityMapper.toCompany(
            serviceRequestDto,
            account
        );
        companyRepository.save(company);
    }

    @Override
    @Transactional
    public CompanyLoginServiceResponseDto login(
        final CompanyLoginServiceRequestDto companyLoginServiceRequestDto
    ) {
        Company company = findByEmail(companyLoginServiceRequestDto.email());

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
                company.tokenId(),
                jwtUtil.substringToken(refreshToken)
            )
        );

        return new CompanyLoginServiceResponseDto(accessToken);
    }

    @Override
    @Transactional
    public void logout(
        final Company company
    ) {
        RefreshToken refreshToken = refreshTokenRepository.findById(company.tokenId())
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.FORBIDDEN));
        refreshTokenRepository.delete(refreshToken);

        company.deleteRefreshToken();
        companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyReadOnceServiceResponseDto readOnceCompany(
        Company company
    ) {
        return companyEntityMapper.toCompanyReadOnceServiceResponseDto(company);
    }

    private Company findByEmail(String email) {
        return companyRepository.findByEmail(email)
            .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_COMPANY));
    }
}
