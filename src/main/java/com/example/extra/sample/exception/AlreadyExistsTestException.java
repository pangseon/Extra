package com.example.extra.sample.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class AlreadyExistsTestException extends CustomException {

    public AlreadyExistsTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
