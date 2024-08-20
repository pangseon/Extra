package com.example.extra.domain.jobpost.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundJobPostException extends CustomException {

    public NotFoundJobPostException(final JobPostErrorCode errorCode) {
        super(errorCode);
    }
}