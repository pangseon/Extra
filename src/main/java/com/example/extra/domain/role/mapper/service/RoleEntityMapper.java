package com.example.extra.domain.role.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import com.example.extra.domain.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {

    Role toRole(RoleCreateServiceRequestDto roleCreateServiceRequestDto, JobPost jobPost);
}
