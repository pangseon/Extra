package com.example.extra.domain.company.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CompanyErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "잘못된 아이디 입력"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "잘못된 비밀번호 입력"),

    // 403
    FORBIDDEN(HttpStatus.FORBIDDEN, "권한 없음. 로그인 하세요."),

    // 404
    NOT_FOUND_COMPANY(HttpStatus.NOT_FOUND, "찾을 수 없는 회사"),
    ;


    private final HttpStatus status;
    private final String message;

    CompanyErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
