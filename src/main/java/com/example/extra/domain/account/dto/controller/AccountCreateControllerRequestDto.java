package com.example.extra.domain.account.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "회원 가입 작성 DTO")
public record AccountCreateControllerRequestDto(
    @Schema(description = "이메일", example = "user@naver.com")
    @NotBlank(message = "이메일은 필수 항목입니다")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "이메일의 형식을 지켜주세요"
    )
    String email,
    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호는 필수 항목입니다")
    String password,
    @Schema(description = "사용자 역할")
    @NotBlank(message = "사용자 역할은 필수 항목입니다")
    @Pattern(
        regexp = "^(USER|COMPANY|ADMIN)$",
        message = "USER, COMPANY, ADMIN 중 하나를 입력해주세요"
    )
    String userRole
) {

}
