package com.example.extra.domain.schedule.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface ScheduleEntityMapper {

    @Mapping(source = "jobPost",target ="jobPost")
    Schedule toSchedule(ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto, JobPost jobPost);
/*    @Mapping(source = "company.name",target = "company_name")
    JobPostServiceResponseDto toJobPostServiceResponseDto(JobPost jobPost, Company company);
    List<JobPostServiceResponseDto> toListJobPostServiceResponseDto(List<JobPost> jobPostList);*/
}
