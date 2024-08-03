package com.example.extra.domain.attendancemanagement.service;

import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AttendanceManagementService {
    List<AttendanceManagementReadServiceResponseDto> getApprovedMemberInfo(
        final Long jobPostId,
        Pageable pageable
    );
    void updateClockInTime(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );
    void updateClockOutTime(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );
    void updateMealCount(
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );

    List<AttendanceManagementCreateExcelServiceResponseDto> getExcelInfo(final Long jobPostId);
}
