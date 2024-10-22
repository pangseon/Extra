package com.example.extra.domain.applicationrequest.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApplicationRequestErrorCode implements ErrorCode {
    // 400
    NOT_ABLE_TO_CANCEL_IN_APPROVED(HttpStatus.BAD_REQUEST, "승인 상태에서는 지원 철회가 불가합니다."),
    NOT_ABLE_TO_APPLY_TO_JOB_POST_DUE_TO_STATUS(HttpStatus.BAD_REQUEST, "해당 공고는 마감되었습니다."),
    NOT_ABLE_TO_APPLY_TO_JOB_POST_DUE_TO_DATE(HttpStatus.BAD_REQUEST, "지원한 다른 공고와 촬영 날짜가 겹칩니다."),
    ALREADY_EXIST(HttpStatus.BAD_REQUEST, "이미 해당 공고에 지원하였습니다."),

    NOT_APPROVED_REQUEST(HttpStatus.BAD_REQUEST, "승인되지 않은 공고입니다."),

    // 403
    NOT_ABLE_TO_ACCESS_APPLICATION_REQUEST_MEMBER(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),

    // 404
    NOT_FOUND_APPLICATION_REQUEST_MEMBER(HttpStatus.NOT_FOUND, "해당 역할은 더이상 유효하지 않습니다."),
    NOT_FOUND_APPLICATION_REQUEST_COMPANY(HttpStatus.NOT_FOUND, "해당 지원자는 더이상 유효하지 않습니다."),
    NOT_FOUND_APPLICATION_REQUEST(HttpStatus.NOT_FOUND, "지원하지 않은 역할입니다."),
    ;

    private final HttpStatus status;
    private final String message;
}