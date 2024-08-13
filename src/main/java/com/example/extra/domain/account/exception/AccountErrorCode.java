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
    INVALID_ROLE_COMPANY(HttpStatus.BAD_REQUEST, "회사 회원가입을 해주세요"),
    DUPLICATION_EMAIL(HttpStatus.BAD_REQUEST, "중복된 아이디"),
    DUPLICATION_ACCOUNT(HttpStatus.BAD_REQUEST, "이미 존재하는 계정"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 이메일 입력"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입력"),

    // 404
    NOT_FOUND_ACCOUNT(HttpStatus.NOT_FOUND, "존재하지 않은 게정입니다"),
    ;

    private final HttpStatus status;
    private final String message;
}
