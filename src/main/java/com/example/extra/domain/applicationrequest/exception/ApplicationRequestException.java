package com.example.extra.domain.applicationrequest.exception;

import com.example.extra.global.exception.CustomException;
import lombok.Getter;

@Getter
public class ApplicationRequestException extends CustomException {

    public ApplicationRequestException(ApplicationRequestErrorCode errorCode) {
        super(errorCode);
    }
}
