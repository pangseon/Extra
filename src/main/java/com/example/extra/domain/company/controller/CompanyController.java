package com.example.extra.domain.company.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.company.dto.controller.CompanyCreateControllerRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
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

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestBody CompanyCreateControllerRequestDto controllerRequestDto
    ) {
        CompanyCreateServiceRequestDto serviceRequestDto =
            companyDtoMapper.toCompanyCreateServiceRequestDto(controllerRequestDto);

        companyService.signup(
            serviceRequestDto
        );

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("")
    public ResponseEntity<?> readOnce(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CompanyReadServiceResponseDto serviceResponseDto =
            companyService.readOnce(userDetails);
        
        return ResponseEntity
            .status(OK)
            .body(serviceResponseDto);
    }
}
