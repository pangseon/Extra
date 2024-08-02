package com.example.extra.domain.attendancemanagement.exception;


import com.example.extra.global.exception.CustomException;

public class NotFoundAttendanceManagementException extends CustomException {
    // 출연 승인하지 않은 사용자를 QR 체크할 때
    public NotFoundAttendanceManagementException(final AttendanceManagementErrorCode errorCode) {
        super(errorCode);
    }
}