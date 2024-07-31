package com.example.extra.domain.jobpost.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.entity.JobPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface JobPostEntityMapper {

    @Mapping(source = "company",target ="company")
    JobPost toJobPost(JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto, Company company);

}
