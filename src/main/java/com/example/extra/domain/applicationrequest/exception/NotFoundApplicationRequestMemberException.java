package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundApplicationRequestMemberException extends CustomException {
    // 삭제하려 했는데 없을 때
    public NotFoundApplicationRequestMemberException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}