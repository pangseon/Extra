package com.example.extra.domain.role.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RoleEntityMapper {

    Role toRole(RoleCreateServiceRequestDto roleCreateServiceRequestDto, JobPost jobPost);
    RoleServiceResponseDto toRoleServiceResponseDto(Role role,Integer min_Age,Integer max_Age);
}
