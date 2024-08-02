package com.example.extra.domain.role.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum RoleErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_ROLE(HttpStatus.BAD_REQUEST, "이미 역할이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_ROLE(HttpStatus.FORBIDDEN, "역할에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_ROLE(HttpStatus.NOT_FOUND, "역할을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}