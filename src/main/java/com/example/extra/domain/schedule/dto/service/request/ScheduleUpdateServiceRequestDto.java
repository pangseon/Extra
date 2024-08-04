package com.example.extra.domain.schedule.dto.service.request;

import java.time.LocalDate;

public record ScheduleUpdateServiceRequestDto(
    LocalDate calender
) {

}
