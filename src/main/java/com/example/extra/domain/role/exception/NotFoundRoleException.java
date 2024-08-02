package com.example.extra.domain.role.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundRoleException extends CustomException {
    public NotFoundRoleException(final RoleErrorCode errorCode) {
        super(errorCode);
    }
}