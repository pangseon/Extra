package com.example.extra.domain.applicationrequest.service;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCreateServiceRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestDeleteServiceRequestDto;
import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.global.enums.ApplyStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ApplicationRequestMemberService {
    void createApplicationRequestMember(
        final ApplicationRequestCreateServiceRequestDto applicationRequestCreateServiceRequestDto
    );
    // TODO - RoleReadResponseDto 정해지면 바꾸기
    Slice<RoleCreateControllerRequestDto> getAppliedRoles(Pageable pageable);

    // TODO - RoleReadResponseDto 정해지면 바꾸기
    Slice<RoleCreateControllerRequestDto> getAppliedRolesByStatus(
        final ApplyStatus applyStatus,
        Pageable pageable
    );

    void deleteApplicationRequestMember(
        final ApplicationRequestDeleteServiceRequestDto applicationRequestDeleteServiceRequestDto
    );
}
