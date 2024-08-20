package com.example.extra.domain.jobpost.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.controller.JobPostUpdateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface JobPostDtoMapper {

    JobPostCreateServiceRequestDto toJobPostCreateServiceDto(
        JobPostCreateControllerRequestDto jobPostCreateControllerRequestDto
    );

    JobPostUpdateServiceRequestDto toJobPostUpdateServiceDto(
        JobPostUpdateControllerRequestDto jobPostUpdateControllerRequestDto
    );

}
