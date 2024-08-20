package com.example.extra.domain.schedule.exception;


import com.example.extra.global.exception.CustomException;

public class ScheduleException extends CustomException {

    public ScheduleException(final ScheduleErrorCode errorCode) {
        super(errorCode);
    }
}