package com.example.extra.domain.attendancemanagement.controller;

import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import com.example.extra.domain.attendancemanagement.mapper.dto.AttendanceManagementDtoMapper;
import com.example.extra.domain.attendancemanagement.service.AttendanceManagementService;
import com.example.extra.domain.attendancemanagement.util.ExcelFile;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance-management/company")
@RestController
public class AttendanceManagementController {
    private final AttendanceManagementService attendanceManagementService;
    private final AttendanceManagementDtoMapper attendanceManagementDtoMapper;

    // 해당 공고 출연자들(출연자 목록 화면)
    @GetMapping("/{jobPostId}/members")
    public ResponseEntity<?> readAllAttendanceManagementByJobPost(
        @PathVariable(name = "jobPostId") Long jobPostId,
        @PageableDefault(size = 10, sort = "roleName", direction = Direction.ASC) Pageable pageable
    ){
        List<AttendanceManagementReadServiceResponseDto> attendanceManagementReadServiceResponseDtoList =
            attendanceManagementService.getApprovedMemberInfo(
                jobPostId,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(attendanceManagementReadServiceResponseDtoList);
    }
    // QR 출근 체크
    @PutMapping("/{jobPostId}/clock-in")
    public ResponseEntity<?> updateAttendanceManagementClockInTime(
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestPart(name = "attendanceManagementUpdateControllerRequestDto")
        AttendanceManagementUpdateControllerRequestDto attendanceManagementUpdateControllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                attendanceManagementUpdateControllerRequestDto
            );
        attendanceManagementService.updateClockInTime(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // QR 퇴근 체크
    @PutMapping("/{jobPostId}/clock-out")
    public ResponseEntity<?> updateAttendanceManagementClockOutTime(
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestPart(name = "attendanceManagementUpdateControllerRequestDto")
        AttendanceManagementUpdateControllerRequestDto attendanceManagementUpdateControllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                attendanceManagementUpdateControllerRequestDto
            );
        attendanceManagementService.updateClockOutTime(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // QR 식사 체크
    @PutMapping("/{jobPostId}/meal-count")
    public ResponseEntity<?> updateAttendanceManagementMealCount(
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestPart(name = "attendanceManagementUpdateControllerRequestDto")
        AttendanceManagementUpdateControllerRequestDto attendanceManagementUpdateControllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                attendanceManagementUpdateControllerRequestDto
            );
        attendanceManagementService.updateMealCount(
            jobPostId,
            attendanceManagementUpdateServiceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // 출퇴근 정보 엑셀 파일
    @GetMapping("/{jobPostId}/excel")
    public void downloadAttendanceInfo(
        @PathVariable(name = "jobPostId") Long jobPostId,
        HttpServletResponse response
    ) throws IOException {
        List<AttendanceManagementCreateExcelServiceResponseDto> attendanceManagementCreateExcelServiceResponseDtoList =
            attendanceManagementService.getExcelInfo(jobPostId);
        ExcelFile excelFile = new ExcelFile(attendanceManagementCreateExcelServiceResponseDtoList);

        // TODO - 파일 이름에 공고 이름 사용하려면 jobPost name 관련 service 이용?
        String JobPostName = "공고1";
        String fileName = new String(JobPostName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1).concat(".xlsx");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=".concat(fileName);

        response.setContentType("ms-vnd/excel");
        response.setHeader(headerKey, headerValue);

        excelFile.write(response.getOutputStream());
    }
}