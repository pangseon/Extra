package com.example.extra.domain.attendancemanagement.exception;


import com.example.extra.global.exception.CustomException;

public class AlreadyClockedOutException extends CustomException {
    // QR 인식 시 퇴근 체크 된 사람을 또 체크하려 할 때
    public AlreadyClockedOutException(final AttendanceManagementErrorCode errorCode) {
        super(errorCode);
    }
}