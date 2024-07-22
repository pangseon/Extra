package com.example.extra.sample.exception;


import com.mju.lighthouseai.global.exception.CustomException;
import com.mju.lighthouseai.global.exception.ErrorCode;

public class NotFoundTestException extends CustomException {

    public NotFoundTestException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
