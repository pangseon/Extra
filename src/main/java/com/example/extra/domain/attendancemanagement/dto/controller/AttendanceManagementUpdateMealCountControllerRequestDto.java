package com.example.extra.domain.attendancemanagement.dto.controller;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AttendanceManagementUpdateMealCountControllerRequestDto(
    // TODO - 이름과 생년월일로 출연자 식별할 수 있을까
    @NotNull(message = "memberName은 필수 정보 입니다.")
    String memberName,
    @NotNull(message = "memberBirthday는 필수 정보 입니다.")
    LocalDate memberBirthday
) {

}
