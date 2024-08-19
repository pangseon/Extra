package com.example.extra.domain.company.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadServiceResponseDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.mapper.entity.CompanyEntityMapper;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.company.service.CompanyService;
import com.example.extra.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    // repository
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    // mapper
    private final CompanyEntityMapper companyEntityMapper;

    @Override
    @Transactional
    public void signup(
        final CompanyCreateServiceRequestDto serviceRequestDto
    ) {
        Account account = accountRepository.findById(serviceRequestDto.accountId())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));

        // 이미 회원 가입한 계정
        companyRepository.findByAccount(account)
            .ifPresent(a -> {
                throw new AccountException(AccountErrorCode.DUPLICATION_ACCOUNT);
            });

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
    public CompanyReadServiceResponseDto readOnce(
        UserDetailsImpl userDetails
    ) {
        Company company = companyRepository.findByAccount(userDetails.getAccount())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
        return companyEntityMapper.toCompanyReadOnceServiceResponseDto(company);
    }
}
