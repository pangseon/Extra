package com.example.extra.domain.schedule.dto.service;

import java.time.LocalDate;

public record ScheduleCreateServiceRequestDto(
    LocalDate calender
) {

}
