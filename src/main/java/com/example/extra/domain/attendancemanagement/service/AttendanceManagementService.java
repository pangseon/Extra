package com.example.extra.domain.attendancemanagement.service;

import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import com.example.extra.domain.company.entity.Company;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AttendanceManagementService {
    List<AttendanceManagementReadServiceResponseDto> getApprovedMemberInfo(
        final Company company,
        final Long jobPostId,
        Pageable pageable
    );
    void updateClockInTime(
        final Company company,
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );
    void updateClockOutTime(
        final Company company,
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );
    void updateMealCount(
        final Company company,
        final Long jobPostId,
        final AttendanceManagementUpdateServiceRequestDto attendanceManagementUpdateServiceRequestDto
    );

    List<AttendanceManagementCreateExcelServiceResponseDto> getExcelInfo(
        final Company company,
        final Long jobPostId
    );
    String getJobPostTitle(final Long jobPostId);
}
