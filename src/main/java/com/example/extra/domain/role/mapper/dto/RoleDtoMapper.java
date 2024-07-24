package com.example.extra.domain.role.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.JobPostCreateServiceRequestDto;
import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RoleDtoMapper {

    JobPostCreateServiceRequestDto toJobPostCreateServiceDto(
      JobPostCreateControllerRequestDto jobPostCreateControllerRequestDto
    );
    List<RoleCreateServiceRequestDto> toRoleCreateServiceRequestDtoList(
    List<RoleCreateControllerRequestDto> roleCreateControllerRequestDtoList);

}
