package com.example.extra.domain.company.controller;

import com.example.extra.domain.company.mapper.dto.CompanyDtoMapper;
import com.example.extra.domain.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDtoMapper companyDtoMapper;


}
