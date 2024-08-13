package com.example.extra.domain.company.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadOnceServiceResponseDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.mapper.entity.CompanyEntityMapper;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.company.service.CompanyService;
import com.example.extra.global.security.JwtUtil;
import com.example.extra.global.security.UserDetailsImpl;
import com.example.extra.global.security.repository.RefreshTokenRepository;
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
    @Transactional(readOnly = true)
    public CompanyReadOnceServiceResponseDto readOnceCompany(
        UserDetailsImpl userDetails
    ) {
        Company company = companyRepository.findByAccount(userDetails.getAccount())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
        return companyEntityMapper.toCompanyReadOnceServiceResponseDto(company);
    }
}
