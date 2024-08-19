package com.example.extra.domain.account.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Schema(name = "로그인 요청 DTO")
public record AccountLoginControllerRequestDto(
    @Schema(description = "이메일", example = "user@naver.com")
    @NotNull(message = "이메일은 필수 항목입니다")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "이메일의 형식을 지켜주세요"
    )
    String email,
    @Schema(description = "비밀번호")
    @NotNull(message = "비밀번호는 필수 항목입니다")
    String password
) {

}
