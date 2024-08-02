package com.example.extra.domain.member.exception;

import com.example.extra.global.exception.CustomException;

public class MemberException extends CustomException {

    public MemberException(final MemberErrorCode errorCode) {
        super(errorCode);
    }
}
