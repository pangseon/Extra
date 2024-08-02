package com.example.extra.domain.schedule.dto.controller;

import java.time.LocalDate;

public record ScheduleCreateControllerRequestDto(
    LocalDate calender
) {

}
