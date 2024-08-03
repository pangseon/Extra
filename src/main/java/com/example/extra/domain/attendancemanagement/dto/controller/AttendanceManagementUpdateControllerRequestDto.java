package com.example.extra.domain.attendancemanagement.dto.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceManagementUpdateControllerRequestDto(
    // TODO - 이름과 생년월일로 출연자 식별할 수 있을까
    String memberName,
    LocalDate memberBirthday,
    LocalDateTime time
) {

}
