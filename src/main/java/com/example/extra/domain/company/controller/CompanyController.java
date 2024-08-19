package com.example.extra.domain.company.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.company.dto.controller.CompanyCreateControllerRequestDto;
import com.example.extra.domain.company.dto.service.request.CompanyCreateServiceRequestDto;
import com.example.extra.domain.company.dto.service.response.CompanyReadServiceResponseDto;
import com.example.extra.domain.company.mapper.dto.CompanyDtoMapper;
import com.example.extra.domain.company.service.CompanyService;
import com.example.extra.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Company", description = "업체 정보 API")
@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyDtoMapper companyDtoMapper;

    @Operation(
        summary = "업체 회원 가입",
        description = "회원 가입 [과정 2-2] : account id와 업체 정보를 입력해서 회원 가입 진행"
    )
    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @Valid @RequestBody CompanyCreateControllerRequestDto controllerRequestDto
    ) {
        CompanyCreateServiceRequestDto serviceRequestDto =
            companyDtoMapper.toCompanyCreateServiceRequestDto(controllerRequestDto);

        companyService.signup(
            serviceRequestDto
        );

        return ResponseEntity.status(CREATED).build();
    }

    @Operation(
        summary = "업체 정보 단건 조회",
        description = "업체 정보 단건 조회하기"
    )
    @ApiResponse(description = "업체 정보 단건 조히 성공", content = @Content(schema = @Schema(implementation = CompanyReadServiceResponseDto.class)))
    @GetMapping("")
    public ResponseEntity<CompanyReadServiceResponseDto> readOnce(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CompanyReadServiceResponseDto serviceResponseDto =
            companyService.readOnce(userDetails.getAccount());

        return ResponseEntity
            .status(OK)
            .body(serviceResponseDto);
    }
}
