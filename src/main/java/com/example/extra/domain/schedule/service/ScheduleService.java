package com.example.extra.domain.schedule.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleCreateServiceResponseDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleServiceResponseDto;
import java.util.List;

public interface ScheduleService {

    ScheduleCreateServiceResponseDto createSchedule(
        Long jobPost_id,
        Account account,
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto);

    void updateSchedule(
        Long jobPost_id,
        Long schedule_id,
        Account account,
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    );

    void deleteSchedule(
        Long jobPost_id,
        Long schedule_id,
        Account account
    );

    ScheduleServiceResponseDto readSchedule(
        Long jobPost_id,
        Long schedule_id
    );

    List<ScheduleServiceResponseDto> readAllSchedule(Long jobPost_id);
}
