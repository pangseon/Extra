package com.example.extra.domain.refreshtoken.exception;

import com.example.extra.global.exception.CustomException;
import lombok.Getter;

@Getter
public class TokenException extends CustomException {

    public TokenException(TokenErrorCode errorCode) {
        super(errorCode);
    }
}
