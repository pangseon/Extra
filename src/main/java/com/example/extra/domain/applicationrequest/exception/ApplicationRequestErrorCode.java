package com.example.extra.domain.applicationrequest.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationRequestErrorCode implements ErrorCode {
    // 400
    NOT_ABLE_TO_CANCEL_APPLICATION_REQUEST_MEMBER(HttpStatus.BAD_REQUEST, "승인 상태에서는 지원 철회가 불가합니다."),
    NOT_FOUND_APPLICATION_REQUEST_MEMBER(HttpStatus.NOT_FOUND, "해당 역할은 더이상 유효하지 않습니다.");
    private final HttpStatus status;
    private final String message;
}