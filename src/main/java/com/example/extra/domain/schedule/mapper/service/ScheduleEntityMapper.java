package com.example.extra.domain.schedule.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleServiceResponseDto;
import com.example.extra.domain.schedule.entity.Schedule;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ScheduleEntityMapper {

    @Mapping(source = "jobPost",target ="jobPost")
    Schedule toSchedule(ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto, JobPost jobPost);
    ScheduleServiceResponseDto toScheduleServiceResponseDto(Schedule schedule);
    List<ScheduleServiceResponseDto> toListScheduleServiceResponseDto(List<Schedule> scheduleList);
}
