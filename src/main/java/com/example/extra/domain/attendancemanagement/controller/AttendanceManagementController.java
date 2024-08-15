package com.example.extra.domain.attendancemanagement.controller;

import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateMealCountControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import com.example.extra.domain.attendancemanagement.mapper.dto.AttendanceManagementDtoMapper;
import com.example.extra.domain.attendancemanagement.service.AttendanceManagementService;
import com.example.extra.domain.attendancemanagement.util.ExcelFile;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/attendance-management/company")
@RestController
public class AttendanceManagementController {

    private final AttendanceManagementService attendanceManagementService;
    private final AttendanceManagementDtoMapper attendanceManagementDtoMapper;

    // 해당 공고 출연자들(출연자 목록 화면)
    @GetMapping("/jobposts/{jobPostId}/members")
    public ResponseEntity<?> readAllAttendanceManagementByJobPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPostId") Long jobPostId,
        @PageableDefault(size = 10, sort = "roleName", direction = Direction.ASC) Pageable pageable
    ) {
        List<AttendanceManagementReadServiceResponseDto> serviceResponseDtoList =
            attendanceManagementService.getApprovedMemberInfo(
                userDetails.getAccount(),
                jobPostId,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDtoList);
    }

    // QR 출근 체크
    @PutMapping("/jobposts/{jobPostId}/clock-in")
    public ResponseEntity<?> updateAttendanceManagementClockInTime(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestBody AttendanceManagementUpdateControllerRequestDto controllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto serviceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                controllerRequestDto
            );
        attendanceManagementService.updateClockInTime(
            userDetails.getAccount(),
            jobPostId,
            serviceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // QR 퇴근 체크
    @PutMapping("/jobposts/{jobPostId}/clock-out")
    public ResponseEntity<?> updateAttendanceManagementClockOutTime(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestBody AttendanceManagementUpdateControllerRequestDto controllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto serviceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                controllerRequestDto
            );
        attendanceManagementService.updateClockOutTime(
            userDetails.getAccount(),
            jobPostId,
            serviceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // QR 식사 체크
    @PutMapping("/jobposts/{jobPostId}/meal-count")
    public ResponseEntity<?> updateAttendanceManagementMealCount(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPostId") Long jobPostId,
        @Valid @RequestBody AttendanceManagementUpdateMealCountControllerRequestDto controllerRequestDto
    ) {
        AttendanceManagementUpdateServiceRequestDto serviceRequestDto =
            attendanceManagementDtoMapper.toAttendanceManagementUpdateServiceRequestDto(
                controllerRequestDto
            );
        attendanceManagementService.updateMealCount(
            userDetails.getAccount(),
            jobPostId,
            serviceRequestDto
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 출퇴근 정보 엑셀 파일
    @GetMapping("/jobposts/{jobPostId}/excel")
    public void downloadAttendanceInfo(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobPostId") Long jobPostId,
        HttpServletResponse response
    ) throws IOException {
        List<AttendanceManagementCreateExcelServiceResponseDto> serviceResponseDtoList =
            attendanceManagementService.getExcelInfo(
                userDetails.getAccount(),
                jobPostId
            );
        ExcelFile excelFile = new ExcelFile(serviceResponseDtoList);

        String fileName = attendanceManagementService.getJobPostTitle(jobPostId).concat(".xlsx");
        // UTF-8로 파일명 인코딩
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
            .replace("+", "%20"); // 공백을 '+' 대신 '%20'으로 변환
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename*=utf-8''" + encodedFileName + ";";

        response.setContentType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(headerKey, headerValue);

        excelFile.write(response.getOutputStream());
    }
}