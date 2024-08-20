package com.example.extra.domain.schedule.dto.service.response;

import java.time.LocalDate;

public record ScheduleReadServiceResponseDto(
    Long id,
    LocalDate calender
) {

}
