package com.example.extra.domain.account.exception;

import com.example.extra.global.exception.CustomException;

public class AccountException extends CustomException {

    public AccountException(final AccountErrorCode errorCode) {
        super(errorCode);
    }
}
