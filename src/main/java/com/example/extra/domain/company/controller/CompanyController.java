package com.example.extra.domain.company.controller;

import com.example.extra.domain.company.dto.controller.CompanyCreateControllerRequestDto;
import com.example.extra.domain.company.dto.service.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.mapper.dto.CompanyDtoMapper;
import com.example.extra.domain.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDtoMapper companyDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestBody CompanyCreateControllerRequestDto companyCreateControllerRequestDto
    ) {
        CompanyCreateServiceRequestDto companyCreateServiceRequestDto =
            companyDtoMapper.toCompanyCreateServiceRequestDto(companyCreateControllerRequestDto);

        companyService.signup(
            companyCreateServiceRequestDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
