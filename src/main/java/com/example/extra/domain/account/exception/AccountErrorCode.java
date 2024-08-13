package com.example.extra.domain.account.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AccountErrorCode implements ErrorCode {
    // 400
    INVALID_ROLE_USER(HttpStatus.BAD_REQUEST, "개인 회원 회원가입을 해주세요"),

    // 404
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "존재하지 않은 게정입니다"),
    ;

    private final HttpStatus status;
    private final String message;
}
