package com.example.extra.domain.schedule.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.dto.controller.JobPostUpdateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.controller.ScheduleCreateControllerRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ScheduleDtoMapper {

    ScheduleCreateServiceRequestDto toScheduleCreateServiceDto(
      ScheduleCreateControllerRequestDto scheduleCreateControllerRequestDto
    );
    JobPostUpdateServiceRequestDto toJobPostUpdateServiceDto(
        JobPostUpdateControllerRequestDto jobPostUpdateControllerRequestDto
    );

}
