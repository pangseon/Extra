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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

@Tag(name = "Schedule", description = "공고글 일정 정보 API")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ScheduleController {

    // service
    private final ScheduleService scheduleService;

    // mapper
    private final ScheduleDtoMapper scheduleDtoMapper;

    @Operation(
        summary = "[업체] 일정 생성",
        description = "job post id와 일정 정보를 입력해서 해당 id를 가지는 공고글에 새로운 일정을 생성합니다."
    )
    @PostMapping("/{jobPost_id}/schedules")
    public ResponseEntity<ScheduleCreateServiceResponseDto> createSchedule(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @Valid @RequestBody ScheduleCreateControllerRequestDto controllerRequestDto
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

    @Operation(
        summary = "[업체] 일정 수정",
        description = "공고글 id와 일정 id, 수정 정보를 입력해서 일정을 수정합니다."
    )
    @PutMapping("/{jobPost_id}/schedules/{schedule_id}")
    public ResponseEntity<Void> updateSchedule(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "schedule_id") Long scheduleId,
        @Valid @RequestBody ScheduleUpdateControllerRequestDto controllerRequestDto
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

    @Operation(
        summary = "[업체] 일정 삭제",
        description = "공고글 id, 일정 id를 사용해서 해당 일정을 삭제합니다."
    )
    @DeleteMapping("/{jobPost_id}/schedules/{schedule_id}")
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

    @Operation(
        summary = "일정 단건 조회",
        description = "공고글 id와 일정 id를 통해 일정 정보를 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "일정 단건 조회 성공", content = @Content(schema = @Schema(implementation = ScheduleReadServiceResponseDto.class)))
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
            ));
    }

    @Operation(
        summary = "일정 전체 조회",
        description = "공고글 id를 통해 해당 공고글의 일정 정보를 전체 조회합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "일정 단건 조회 성공",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = ScheduleReadServiceResponseDto.class)))
    )
    @GetMapping("/{jobPost_id}/schedules")
    public ResponseEntity<List<ScheduleReadServiceResponseDto>> readAllSchedule(
        @PathVariable(name = "jobPost_id") Long jobPostId
    ) {
        return ResponseEntity
            .status(OK)
            .body(scheduleService.readAllSchedule(jobPostId));
    }
}
