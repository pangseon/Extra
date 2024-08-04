package com.example.extra.domain.schedule.service;

import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;

public interface ScheduleService {
    void createSchedule(
        Long JobPost_id
        ,ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto);

}
