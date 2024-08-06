package com.example.extra.domain.attendancemanagement.exception;


import com.example.extra.global.exception.CustomException;

public class AttendanceManagementException extends CustomException {
    public AttendanceManagementException(final AttendanceManagementErrorCode errorCode) {
        super(errorCode);
    }
}