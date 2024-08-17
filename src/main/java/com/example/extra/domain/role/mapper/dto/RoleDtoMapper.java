package com.example.extra.domain.role.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.dto.controller.RoleUpdateControllerRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RoleDtoMapper {

    RoleCreateServiceRequestDto toRoleCreateServiceDto(
      RoleCreateControllerRequestDto roleCreateControllerRequestDto
    );
    RoleUpdateServiceRequestDto toRoleUpdateServiceDto(
        RoleUpdateControllerRequestDto roleUpdateControllerRequestDto
    );
}
