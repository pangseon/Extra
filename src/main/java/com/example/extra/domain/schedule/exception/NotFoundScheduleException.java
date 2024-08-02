package com.example.extra.domain.schedule.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundScheduleException extends CustomException {
    public NotFoundScheduleException(final ScheduleErrorCode errorCode) {
        super(errorCode);
    }
}