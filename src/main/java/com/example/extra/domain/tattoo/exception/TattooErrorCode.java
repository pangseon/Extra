package com.example.extra.domain.tattoo.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TattooErrorCode implements ErrorCode {

    // 404
    NOT_FOUND_TATTOO(HttpStatus.NOT_FOUND, "타투를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}