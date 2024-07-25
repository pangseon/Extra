package com.example.extra.domain.applicationrequest.exception;


import com.example.extra.global.exception.CustomException;

public class AlreadyExistApplicationRequestCompanyException extends CustomException {
    public AlreadyExistApplicationRequestCompanyException(final ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}