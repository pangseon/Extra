package com.example.extra.domain.applicationrequest.service;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ApplicationRequestMemberService {
    void createApplicationRequestMember(Long roleId);
    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(Pageable pageable);

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final ApplyStatus applyStatus,
        Pageable pageable
    );

    List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final long roleId,
        Pageable pageable
    );

    void updateStatus(
        Long roleId,
        Long memberId,
        ApplyStatus applyStatus
    );

    void deleteApplicationRequestMember(Long roleId);
}
