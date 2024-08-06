package com.example.extra.domain.attendancemanagement.mapper.dto;

import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateMealCountControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface AttendanceManagementDtoMapper {
    AttendanceManagementUpdateServiceRequestDto toAttendanceManagementUpdateServiceRequestDto(
        AttendanceManagementUpdateControllerRequestDto controllerRequestDto
    );
    AttendanceManagementUpdateServiceRequestDto toAttendanceManagementUpdateServiceRequestDto(
        AttendanceManagementUpdateMealCountControllerRequestDto controllerRequestDto
    );
}
