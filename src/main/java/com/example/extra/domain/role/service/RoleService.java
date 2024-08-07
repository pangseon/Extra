package com.example.extra.domain.role.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import java.util.List;

public interface RoleService {
    void createRole(
        Long jobPost_id,
        Company company,
        RoleCreateServiceRequestDto roleCreateServiceRequestDto);
    void updateRole(
        Long jobPost_id,
        Long role_id,
        Company company,
        RoleUpdateServiceRequestDto roleUpdateServiceRequestDto);
    void deleteRole(
        Long jobPost_id,
        Long role_id,
        Company company);
    RoleServiceResponseDto readRole(
      Long jobPost_id,
      Long role_id
    );
    List<RoleServiceResponseDto> readAllRole(Long jobPost_id);
}
