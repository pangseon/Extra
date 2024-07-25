package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotAbleToCancelApplicationRequestMemberException extends CustomException {
    // 지원 요청이 승인된 상태에서 지원 요청을 취소하려 할 때
    public NotAbleToCancelApplicationRequestMemberException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}