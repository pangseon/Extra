package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class AlreadyExistApplicationRequestMemberException extends CustomException {
    public AlreadyExistApplicationRequestMemberException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}