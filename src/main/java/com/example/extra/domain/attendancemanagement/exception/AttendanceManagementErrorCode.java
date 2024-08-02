package com.example.extra.domain.attendancemanagement.exception;

import com.example.extra.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AttendanceManagementErrorCode implements ErrorCode {
    ALREADY_CLOCKED_IN(HttpStatus.BAD_REQUEST, "이미 출근 처리가 완료되었습니다."),
    ALREADY_CLOCKED_OUT(HttpStatus.BAD_REQUEST, "이미 퇴근 처리가 완료되었습니다."),

    // TODO - 해당 경우에 대해 예외처리를 할지 논의 필요
    // // 해당 공고 작성하지 않은 계정에는 관리 화면이 안뜰 것이기에 필요 없음
    // FORBIDDEN_ACCESS_ATTENDANCE_MANAGEMENT(HttpStatus.FORBIDDEN, "해당 촬영에 접근 권한이 없습니다. 해당 촬영의 공고를 작성한 계정으로 다시 시도하세요."),

    NOT_FOUND_ATTENDANCE_MANAGEMENT(HttpStatus.NOT_FOUND, "해당 촬영의 출연자로 등록되어 있지 않습니다.");

    private final HttpStatus status;
    private final String message;
}