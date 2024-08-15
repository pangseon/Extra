package com.example.extra.domain.refreshtoken.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    ACCESS_KEY_REISSUANCE(HttpStatus.FORBIDDEN, "access token 재발급"),
    NOT_FOUND_TOKEN(HttpStatus.FORBIDDEN, "토큰이 없습니다. 로그인 해주세요."),
    INVALID_TOKEN(HttpStatus.FORBIDDEN, "해당 토큰은 유효하지 않습니다. 로그인 해주세요.");

    private final HttpStatus status;
    private final String message;
}
