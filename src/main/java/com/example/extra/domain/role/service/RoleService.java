package com.example.extra.domain.role.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import java.util.List;

public interface RoleService {

    void createRole(
        Long jobPost_id,
        Account account,
        RoleCreateServiceRequestDto roleCreateServiceRequestDto);

    void updateRole(
        Long jobPost_id,
        Long role_id,
        Account account,
        RoleUpdateServiceRequestDto roleUpdateServiceRequestDto);

    void deleteRole(
        Long jobPost_id,
        Long role_id,
        Account account
    );

    RoleServiceResponseDto readRole(
        Long jobPost_id,
        Long role_id
    );

    List<RoleServiceResponseDto> readAllRole(Long jobPost_id);
}
