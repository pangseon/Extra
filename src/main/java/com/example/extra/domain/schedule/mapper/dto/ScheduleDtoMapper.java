package com.example.extra.domain.schedule.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.schedule.dto.controller.ScheduleCreateControllerRequestDto;
import com.example.extra.domain.schedule.dto.controller.ScheduleUpdateControllerRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ScheduleDtoMapper {

    ScheduleCreateServiceRequestDto toScheduleCreateServiceDto(
        ScheduleCreateControllerRequestDto scheduleCreateControllerRequestDto
    );

    ScheduleUpdateServiceRequestDto toScheduleUpdateServiceDto(
        ScheduleUpdateControllerRequestDto scheduleUpdateControllerRequestDto
    );

}
