package com.example.extra.global.security.exception;

import com.example.extra.global.exception.CustomException;
import com.example.extra.global.exception.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    @Override
    public ErrorCode getErrorCode() {
        return super.getErrorCode();
    }
}
