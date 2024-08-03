package com.example.extra.domain.schedule.controller;

import com.example.extra.domain.schedule.dto.controller.ScheduleCreateControllerRequestDto;
import com.example.extra.domain.schedule.dto.service.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.mapper.dto.ScheduleDtoMapper;
import com.example.extra.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ScheduleDtoMapper scheduleDtoMapper;

    @PostMapping("/{JobPost_id}/schedules/create")
    public ResponseEntity<?> createSchedule(
        @PathVariable Long jobPost_id,
        @RequestBody ScheduleCreateControllerRequestDto createControllerRequestDto
    ){

        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto =
            scheduleDtoMapper.toScheduleCreateServiceDto(createControllerRequestDto);
        scheduleService.createSchedule(jobPost_id,scheduleCreateServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
