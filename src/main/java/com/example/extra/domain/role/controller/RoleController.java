package com.example.extra.domain.role.controller;

import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.dto.controller.RoleUpdateControllerRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleCreateServiceResponseDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import com.example.extra.domain.role.mapper.dto.RoleDtoMapper;
import com.example.extra.domain.role.service.RoleService;
import com.example.extra.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/jobposts")
public class RoleController {

    private final RoleDtoMapper roleDtoMapper;
    private final RoleService roleService;

    @PostMapping("/{jobPost_id}/roles")
    public ResponseEntity<RoleCreateServiceResponseDto> createRole(
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody RoleCreateControllerRequestDto controllerRequestDto
    ) {
        RoleCreateServiceRequestDto serviceRequestDto =
            roleDtoMapper.toRoleCreateServiceDto(controllerRequestDto);
        roleService.createRole(jobPostId, userDetails.getAccount(), serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{jobPost_id}/roles/{role_id}")
    public ResponseEntity<?> updateRole(
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "role_id") Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody RoleUpdateControllerRequestDto controllerRequestDto
    ) {
        RoleUpdateServiceRequestDto serviceRequestDto =
            roleDtoMapper.toRoleUpdateServiceDto(controllerRequestDto);
        roleService.updateRole(jobPostId, roleId, userDetails.getAccount(),
            serviceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{jobPost_id}/roles/{role_id}")
    public ResponseEntity<?> deleteRole(
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "role_id") Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        roleService.deleteRole(jobPostId, roleId, userDetails.getAccount());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{jobPost_id}/roles/{role_id}")
    public RoleServiceResponseDto readOnceRole(
        @PathVariable(name = "jobPost_id") Long jobPostId,
        @PathVariable(name = "role_id") Long roleId
    ) {
        return roleService.readRole(jobPostId, roleId);
    }

    @GetMapping("/{jobPost_id}/roles")
    public List<RoleServiceResponseDto> readAllRile(
        @PathVariable(name = "jobPost_id") Long jobPostId) {
        return roleService.readAllRole(jobPostId);
    }

}
