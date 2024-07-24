package com.example.extra.domain.role.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = SPRING)
public interface RoleEntityMapper {

    Role toRole(RoleCreateServiceRequestDto roleCreateServiceRequestDto, JobPost jobPost);

}
