package com.example.extra.domain.applicationrequest.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ApplicationRequestMemberService {

    void createApplicationRequestMember(
        final Account account,
        final Long roleId
    );

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(
        final Account account,
        final Integer year,
        final Integer month,
        final Pageable pageable
    );

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final Account account,
        final ApplyStatus applyStatus,
        final Pageable pageable
    );

    void deleteApplicationRequestMember(
        final Account account,
        final Long applicationRequestMemberId
    );

    List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final Account account,
        final long roleId,
        final Pageable pageable
    );

    void updateStatus(
        final Account account,
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );

    void createAttendanceManagementIfApproved(
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );

    MemberReadServiceResponseDto readOnceApplicationRequestMember(
        Account account,
        Long applicationRequestId
    );
}
