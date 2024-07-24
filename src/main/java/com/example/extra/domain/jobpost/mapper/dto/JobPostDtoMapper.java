package com.example.extra.domain.jobpost.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.JobPostCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface JobPostDtoMapper {

    JobPostCreateServiceRequestDto toJobPostCreateServiceDto(
      JobPostCreateControllerRequestDto jobPostCreateControllerRequestDto
    );

}
