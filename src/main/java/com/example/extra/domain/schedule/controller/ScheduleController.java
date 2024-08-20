package com.example.extra.domain.schedule.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.schedule.dto.controller.ScheduleCreateControllerRequestDto;
import com.example.extra.domain.schedule.dto.controller.ScheduleUpdateControllerRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleCreateServiceResponseDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleReadServiceResponseDto;
import com.example.extra.domain.schedule.mapper.dto.ScheduleDtoMapper;
import com.example.extra.domain.schedule.service.ScheduleService;
import com.example.extra.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

    // service
    private final ScheduleService scheduleService;

    // mapper
    private final ScheduleDtoMapper scheduleDtoMapper;

    @PostMapping("/{jobPost_id}/schedules/company")
    public ResponseEntity<ScheduleCreateServiceResponseDto> createSchedule(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @RequestBody ScheduleCreateControllerRequestDto controllerRequestDto
    ) {
        ScheduleCreateServiceRequestDto serviceRequestDto =
            scheduleDtoMapper.toScheduleCreateServiceDto(controllerRequestDto);

        scheduleService.createSchedule(
            userDetails.getAccount(),
            jobPostId,
            serviceRequestDto
        );

        return ResponseEntity
            .status(CREATED)
            .build();
    }

    @PutMapping("/{jobPost_id}/schedules/{schedule_id}/company")
    public ResponseEntity<Void> updateSchedule(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId,
        @RequestBody ScheduleUpdateControllerRequestDto controllerRequestDto
    ) {
        ScheduleUpdateServiceRequestDto serviceRequestDto =
            scheduleDtoMapper.toScheduleUpdateServiceDto(controllerRequestDto);

        scheduleService.updateSchedule(
            userDetails.getAccount(),
            jobPostId,
            scheduleId,
            serviceRequestDto
        );

        return ResponseEntity
            .status(CREATED)
            .build();
    }

    @DeleteMapping("/{jobPost_id}/schedules/{schedule_id}/company")
    public ResponseEntity<?> deleteSchedule(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId
    ) {
        scheduleService.deleteSchedule(
            userDetails.getAccount(),
            jobPostId,
            scheduleId
        );

        return ResponseEntity
            .status(OK)
            .build();
    }

    @GetMapping("/{jobPost_id}/schedules/{schedule_id}")
    public ResponseEntity<ScheduleReadServiceResponseDto> readSchedule(
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId
    ) {
        return ResponseEntity
            .status(OK)
            .body(scheduleService.readSchedule(
                    jobPostId,
                    scheduleId
                )
            );
    }

    @GetMapping("/{jobPost_id}/schedules")
    public ResponseEntity<List<ScheduleReadServiceResponseDto>> readAllSchedule(
        @PathVariable(name = "jobPost_id") Long jobPostId
    ) {
        return ResponseEntity
            .status(OK)
            .body(scheduleService.readAllSchedule(jobPostId));
    }
}
