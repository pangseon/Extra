package com.example.extra.domain.role.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceReResponseDto;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RoleEntityMapper {

    Role toRole(RoleCreateServiceRequestDto roleCreateServiceRequestDto, JobPost jobPost);
    RoleServiceReResponseDto toRoleServiceReResponseDto(Role role);
    List<RoleServiceReResponseDto> toListRoleServiceReResponseDto(List<Role> roleList);
}
