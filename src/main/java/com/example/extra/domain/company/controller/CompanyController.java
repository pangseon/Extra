package com.example.extra.domain.company.controller;

import com.example.extra.domain.company.dto.controller.CompanyCreateControllerRequestDto;
import com.example.extra.domain.company.dto.controller.CompanyLoginControllerRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyLoginServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyLoginServiceResponseDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadOnceServiceResponseDto;
import com.example.extra.domain.company.mapper.dto.CompanyDtoMapper;
import com.example.extra.domain.company.service.CompanyService;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDtoMapper companyDtoMapper;

    public static final String AUTHORIZATION_HEADER = "Authorization";


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

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid @RequestBody CompanyLoginControllerRequestDto companyLoginControllerRequestDto
    ) {
        CompanyLoginServiceRequestDto companyLoginServiceRequestDto =
            companyDtoMapper.toCompanyLoginServiceRequestDto(companyLoginControllerRequestDto);

        CompanyLoginServiceResponseDto companyLoginServiceResponseDto =
            companyService.login(companyLoginServiceRequestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(
                AUTHORIZATION_HEADER,
                companyLoginServiceResponseDto.token()
            )
            .build();
    }

    @GetMapping("")
    public ResponseEntity<?> readOnceCompany(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CompanyReadOnceServiceResponseDto companyReadOnceServiceResponseDto =
            companyService.readOnceCompany(userDetails);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(companyReadOnceServiceResponseDto);
    }
}
