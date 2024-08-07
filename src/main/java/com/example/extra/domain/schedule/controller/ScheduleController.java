package com.example.extra.domain.schedule.controller;

import com.example.extra.domain.schedule.dto.controller.ScheduleCreateControllerRequestDto;
import com.example.extra.domain.schedule.dto.controller.ScheduleUpdateControllerRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleServiceResponseDto;
import com.example.extra.domain.schedule.mapper.dto.ScheduleDtoMapper;
import com.example.extra.domain.schedule.service.ScheduleService;
import com.example.extra.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jobposts")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleDtoMapper scheduleDtoMapper;

    @PostMapping("/{jobPost_id}/schedules")
    public ResponseEntity<?> createSchedule(
        @PathVariable(name = "jobpost_id") Long jobPostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ScheduleCreateControllerRequestDto createControllerRequestDto
    ) {
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto =
            scheduleDtoMapper.toScheduleCreateServiceDto(createControllerRequestDto);
        scheduleService.createSchedule(jobPostId, userDetails.getCompany(),
            scheduleCreateServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{jobPost_id}/schedules/{schedule_id}")
    public ResponseEntity<?> updateSchedule(
        @PathVariable(name = "jobpost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ScheduleUpdateControllerRequestDto scheduleUpdateControllerRequestDto
    ) {
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto =
            scheduleDtoMapper.toScheduleUpdateServiceDto(scheduleUpdateControllerRequestDto);
        scheduleService.updateSchedule(jobPostId, scheduleId, userDetails.getCompany(),
            scheduleUpdateServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{jobPost_id}/schedules/{schedule_id}")
    public ResponseEntity<?> deleteSchedule(
        @PathVariable(name = "jobpost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        scheduleService.deleteSchedule(jobPostId, scheduleId, userDetails.getCompany());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{jobPost_id}/schedules/{schedule_id}")
    public ScheduleServiceResponseDto readSchedule(
        @PathVariable(name = "jobpost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId
    ) {
        return scheduleService.readSchedule(jobPostId, scheduleId);
    }

    @GetMapping("/{jobPost_id}/schedules")
    public List<ScheduleServiceResponseDto> readAllSchedule(
        @PathVariable(name = "jobpost_id") Long jobPostId
    ) {
        return scheduleService.readAllSchedule(jobPostId);
    }
}
