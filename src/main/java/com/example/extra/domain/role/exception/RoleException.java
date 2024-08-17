package com.example.extra.domain.role.exception;


import com.example.extra.global.exception.CustomException;

public class RoleException extends CustomException {
    public RoleException(final RoleErrorCode errorCode) {
        super(errorCode);
    }
}