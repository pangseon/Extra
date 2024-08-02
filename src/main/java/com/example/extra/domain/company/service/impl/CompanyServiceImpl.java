package com.example.extra.domain.company.service.impl;

import com.example.extra.domain.company.dto.service.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.mapper.entity.CompanyEntityMapper;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyEntityMapper companyEntityMapper;

    private final String ADMIN_TOKEN = "admintoken";

    @Override
    @Transactional
    public void signup(
        CompanyCreateServiceRequestDto companyCreateServiceRequestDto
    ) {
        // 이메일 중복 검사
        String email = companyCreateServiceRequestDto.email();
        companyRepository.findByEmail(email)
            .ifPresent(c -> {
                throw new CompanyException(CompanyErrorCode.ALREADY_EXIST_EMAIL);
            });

        Company company = companyEntityMapper.toCompany(companyCreateServiceRequestDto);
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
}
