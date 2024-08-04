package com.example.extra.domain.schedule.dto.service.response;

import java.time.LocalDate;

public record ScheduleServiceResponseDto(
    Long id,
    LocalDate calender
) {

}
