package com.example.extra.domain.member.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // 403
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "토큰이 없습니다."),

    // 404
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}
