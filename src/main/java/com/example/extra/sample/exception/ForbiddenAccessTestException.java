package com.example.extra.sample.exception;


import com.example.extra.global.exception.CustomException;
import com.example.extra.global.exception.ErrorCode;

public class ForbiddenAccessTestException extends CustomException {

    public ForbiddenAccessTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
