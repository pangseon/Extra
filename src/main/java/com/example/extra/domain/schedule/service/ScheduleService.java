package com.example.extra.domain.schedule.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;

public interface ScheduleService {
    void createSchedule(
        Long jobPost_id,
        Company company,
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto);
    void updateSchedule(
        Long jobPost_id,
        Long schedule_id,
        Company company,
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    );
}
