package com.example.extra.domain.schedule.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleCreateServiceResponseDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleReadServiceResponseDto;
import java.util.List;

public interface ScheduleService {

    ScheduleCreateServiceResponseDto createSchedule(
        Account account,
        Long jobPost_id,
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto);

    void updateSchedule(
        Account account,
        Long jobPost_id,
        Long schedule_id,
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    );

    void deleteSchedule(
        Account account,
        Long jobPost_id,
        Long schedule_id
    );

    ScheduleReadServiceResponseDto readSchedule(
        Long jobPost_id,
        Long schedule_id
    );

    List<ScheduleReadServiceResponseDto> readAllSchedule(Long jobPost_id);
}
