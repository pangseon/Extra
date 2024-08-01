package com.example.extra.global.security.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    ACCESS_KEY_REISSUANCE(HttpStatus.UNAUTHORIZED, "access token 재발급"),
    NOT_FOUND_TOKEN(HttpStatus.UNAUTHORIZED, "refresh 토큰이 업습니다. 로그인 해주세요.");

    private final HttpStatus status;
    private final String message;
}
