package com.example.extra.domain.applicationrequest.service;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ApplicationRequestMemberService {
    void createApplicationRequestMember(final Long roleId);
    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(final Pageable pageable);

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final ApplyStatus applyStatus,
        final Pageable pageable
    );

    List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final long roleId,
        final Pageable pageable
    );

    void updateStatus(
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );
    void createAttendanceManagementIfApproved(
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );

    void deleteApplicationRequestMember(final Long applicationRequestMemberId);
}
