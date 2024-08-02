package com.example.extra.domain.attendancemanagement.mapper.entity;

import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AttendanceManagementEntityMapper {
    @Mapping(target = "memberName", source = "member.name")
    @Mapping(target = "memberBank", source = "member.bank")
    @Mapping(target = "memberAccountNumber", source = "member.accountNumber")
    AttendanceManagementCreateExcelServiceResponseDto toAttendanceManagementCreateExcelServiceResponseDto(
        AttendanceManagement attendanceManagement
    );

    List<AttendanceManagementCreateExcelServiceResponseDto> toAttendanceManagementCreateExcelServiceResponseDtoList(
        List<AttendanceManagement> attendanceManagementList
    );

}
