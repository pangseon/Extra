package com.example.extra.sample.exception;


import com.example.extra.global.exception.CustomException;
import com.example.extra.global.exception.ErrorCode;

public class NotFoundTestException extends CustomException {

    public NotFoundTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
