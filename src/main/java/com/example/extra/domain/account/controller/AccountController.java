package com.example.extra.domain.account.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.account.dto.controller.AccountCreateControllerRequestDto;
import com.example.extra.domain.account.dto.controller.AccountLoginControllerRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountCreateServiceRequestDto;
import com.example.extra.domain.account.dto.service.request.AccountLoginServiceRequestDto;
import com.example.extra.domain.account.dto.service.response.AccountCreateServiceResponseDto;
import com.example.extra.domain.account.dto.service.response.AccountLoginServiceResponseDto;
import com.example.extra.domain.account.mapper.dto.AccountDtoMapper;
import com.example.extra.domain.account.service.AccountService;
import com.example.extra.domain.refreshtoken.dto.RefreshTokenCreateServiceResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Account", description = "계정 공통 정보 API")
@RestController
@RequestMapping("api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final AccountService accountService;
    // mapper
    private final AccountDtoMapper accountDtoMapper;

    @Operation(
        summary = "계정 회원 가입",
        description = "회원 가입 [과정 1] : 이메일, 비밀번호, 역할 정보 입력해서 account 정보를 등록합니다. 반환된 account id를 사용해 member 회원 가입과 company 회원 가입이 가능합니다."
    )
    @ApiResponse(responseCode = "201", description = "계정 작성 성공", content = @Content(schema = @Schema(implementation = AccountCreateServiceResponseDto.class)))
    @PostMapping("/signup")
    public ResponseEntity<AccountCreateServiceResponseDto> signup(
        @Valid @RequestBody AccountCreateControllerRequestDto controllerRequestDto
    ) {
        AccountCreateServiceRequestDto serviceRequestDto =
            accountDtoMapper.toAccountCreateServiceRequestDto(controllerRequestDto);

        return ResponseEntity
            .status(CREATED)
            .body(accountService.signup(serviceRequestDto));
    }

    @Operation(
        summary = "로그인",
        description = "로그인 : 가입한 이메일과 비밀번호를 입력해 access token과 refresh token을 받습니다. access token은 header에 refresh token은 body에 존재합니다."
    )
    @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = RefreshTokenCreateServiceResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "보조 출연자 및 업체 회원가입을 마저 진행해주세요", content = @Content(schema = @Schema(implementation = AccountCreateServiceResponseDto.class)))
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid @RequestBody AccountLoginControllerRequestDto controllerRequestDto
    ) {
        AccountLoginServiceRequestDto serviceRequestDto =
            accountDtoMapper.toAccountLoginServiceRequestDto(controllerRequestDto);

        AccountLoginServiceResponseDto serviceResponseDto =
            accountService.login(serviceRequestDto);

        if (serviceResponseDto.isLogin()) {
            return ResponseEntity
                .status(BAD_REQUEST)
                .body(new AccountCreateServiceResponseDto(serviceResponseDto.accountId()));
        } else {
            return ResponseEntity
                .status(OK)
                .header(
                    AUTHORIZATION_HEADER,
                    serviceResponseDto.accessToken()
                )
                .body(new RefreshTokenCreateServiceResponseDto(serviceResponseDto.refreshToken()));

        }
    }
}
