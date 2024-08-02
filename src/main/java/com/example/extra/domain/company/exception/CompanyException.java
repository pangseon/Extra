package com.example.extra.domain.company.exception;

import com.example.extra.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CompanyException extends CustomException {

    public CompanyException(CompanyErrorCode errorCode) {
        super(errorCode);
    }
}
