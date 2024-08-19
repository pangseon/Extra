package com.example.extra.domain.attendancemanagement.dto.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record AttendanceManagementUpdateMealCountControllerRequestDto(
    // TODO - 이름과 생년월일로 출연자 식별할 수 있을까
    @NotBlank(message = "memberName은 필수 정보 입니다.")
    @Pattern(
        regexp = "^[\\uAC00-\\uD7AFa-zA-Z]{1,10}$",
        message = "memberName은 한글과 영어 알파벳으로만 구성되어야 하며, 길이는 10자 이하이어야 합니다."
    )
    String memberName,
    @NotNull(message = "memberBirthday는 필수 정보 입니다.")
    LocalDate memberBirthday
) {

}
