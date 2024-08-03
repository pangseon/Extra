package com.example.extra.domain.attendancemanagement.mapper.dto;

import com.example.extra.domain.attendancemanagement.dto.controller.AttendanceManagementUpdateControllerRequestDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementUpdateServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AttendanceManagementDtoMapper {
    AttendanceManagementUpdateServiceRequestDto toAttendanceManagementUpdateServiceRequestDto(
        AttendanceManagementUpdateControllerRequestDto controllerRequestDto
    );
}
