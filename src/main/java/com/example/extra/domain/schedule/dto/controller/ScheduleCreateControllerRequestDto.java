package com.example.extra.domain.schedule.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ScheduleCreateControllerRequestDto(
    @Schema(description = "날짜")
    @NotNull(message = "일정을 입력해주세요")
    LocalDate calender
) {

}
