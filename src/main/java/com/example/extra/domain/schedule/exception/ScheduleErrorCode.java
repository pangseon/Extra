package com.example.extra.domain.schedule.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ScheduleErrorCode implements ErrorCode {
    // 400
    ALREADY_EXIST_SCHEDULE(HttpStatus.BAD_REQUEST, "이미 일정이 존재합니다."),

    // 403
    FORBIDDEN_ACCESS_SCHEDULE(HttpStatus.FORBIDDEN, "일정에 접근할 수 없습니다."),

    // 404
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;
}