package com.example.extra.domain.jobpost.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JobPostErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_JOBPOST(HttpStatus.BAD_REQUEST, "이미 공고글이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_JOBPOST(HttpStatus.FORBIDDEN, "공고글에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_JOBPOST(HttpStatus.NOT_FOUND, "공고글을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}