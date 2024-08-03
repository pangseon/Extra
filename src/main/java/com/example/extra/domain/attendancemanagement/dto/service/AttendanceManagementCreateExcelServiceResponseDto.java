package com.example.extra.domain.attendancemanagement.dto.service;

import java.time.LocalDateTime;

public record AttendanceManagementCreateExcelServiceResponseDto(
    String memberName,
    String memberBank,
    String memberAccountNumber,
    LocalDateTime clockInTime,
    LocalDateTime clockOutTime,
    Integer mealCount
) {
}
