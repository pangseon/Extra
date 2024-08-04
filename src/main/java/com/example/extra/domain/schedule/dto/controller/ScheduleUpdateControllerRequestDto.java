package com.example.extra.domain.schedule.dto.controller;

import java.time.LocalDate;

public record ScheduleUpdateControllerRequestDto(
    LocalDate calender
) {

}
