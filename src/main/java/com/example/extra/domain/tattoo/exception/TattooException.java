package com.example.extra.domain.tattoo.exception;


import com.example.extra.global.exception.CustomException;

public class TattooException extends CustomException {
    public TattooException(final TattooErrorCode errorCode) {
        super(errorCode);
    }
}