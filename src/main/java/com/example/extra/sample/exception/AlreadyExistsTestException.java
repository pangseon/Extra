package com.example.extra.sample.exception;


import com.example.extra.global.exception.CustomException;
import com.example.extra.global.exception.ErrorCode;

public class AlreadyExistsTestException extends CustomException {

    public AlreadyExistsTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
