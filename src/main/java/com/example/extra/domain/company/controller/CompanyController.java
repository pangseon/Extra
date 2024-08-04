package com.example.extra.domain.company.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
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

        return ResponseEntity.status(CREATED).build();
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
            .status(OK)
            .header(
                AUTHORIZATION_HEADER,
                companyLoginServiceResponseDto.token()
            )
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        companyService.logout(userDetails.getCompany());
        return ResponseEntity
            .status(OK)
            .build();
    }

    @GetMapping("")
    public ResponseEntity<?> readOnceCompany(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CompanyReadOnceServiceResponseDto companyReadOnceServiceResponseDto =
            companyService.readOnceCompany(userDetails.getCompany());
        return ResponseEntity
            .status(OK)
            .body(companyReadOnceServiceResponseDto);
    }
}
