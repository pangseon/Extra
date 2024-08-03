package com.example.extra.domain.attendancemanagement.exception;


import com.example.extra.global.exception.CustomException;

public class AlreadyClockedInException extends CustomException {
    // QR 인식 시 출근 체크 된 사람을 또 체크하려 할 때
    public AlreadyClockedInException(final AttendanceManagementErrorCode errorCode) {
        super(errorCode);
    }
}