package com.example.extra.domain.applicationrequest.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationRequestErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_APPLICATION_REQUEST_COMPANY(HttpStatus.BAD_REQUEST, "이미 해당 사용자에게 요청되었습니다."),
    ALREADY_EXIST_APPLICATION_REQUEST_MEMBER(HttpStatus.BAD_REQUEST, "이미 해당 역할에 지원되었습니다."),

    NOT_ABLE_TO_CANCEL_APPLICATION_REQUEST_MEMBER(HttpStatus.BAD_REQUEST, "승인 상태에서는 지원 철회가 불가합니다."),

    // 404
    NOT_FOUND_APPLICATION_REQUEST_COMPANY(HttpStatus.NOT_FOUND, "해당 사용자에게 요청한 이력이 없습니다."),
    NOT_FOUND_APPLICATION_REQUEST_MEMBER(HttpStatus.NOT_FOUND, "해당 역할에 지원한 이력이 없습니다.");

    private final HttpStatus status;
    private final String message;
}