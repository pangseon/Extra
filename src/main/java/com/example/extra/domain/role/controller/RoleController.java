package com.example.extra.domain.role.controller;

import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.mapper.dto.RoleDtoMapper;
import com.example.extra.domain.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class RoleController {

    private final RoleDtoMapper roleDtoMapper;
    private final RoleService roleService;
    @PostMapping("/{jobPost_id}/roles/create")
    public ResponseEntity<?> createRole(
        @PathVariable Long jobPost_id,
        @RequestBody RoleCreateControllerRequestDto roleCreateControllerRequestDto
    ){
        RoleCreateServiceRequestDto roleCreateServiceRequestDto =
            roleDtoMapper.toRoleCreateServiceDto(roleCreateControllerRequestDto);
        roleService.createRole(jobPost_id,roleCreateServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
