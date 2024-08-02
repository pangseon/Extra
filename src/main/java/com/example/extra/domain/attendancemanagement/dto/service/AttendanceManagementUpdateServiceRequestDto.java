package com.example.extra.domain.attendancemanagement.dto.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceManagementUpdateServiceRequestDto(
    String memberName,
    LocalDate memberBirthday,
    LocalDateTime time
) {

}
