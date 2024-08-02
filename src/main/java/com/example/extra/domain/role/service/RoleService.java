package com.example.extra.domain.role.service;

import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;

public interface RoleService {
    void createRole(Long jobPost_id, RoleCreateServiceRequestDto roleCreateServiceRequestDto);

}
