package com.example.extra.domain.applicationrequest.service;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ApplicationRequestMemberService {

    void createApplicationRequestMember(
        final Member member,
        final Long roleId
    );

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRoles(
        final Member member,
        final Pageable pageable
    );

    List<ApplicationRequestMemberReadServiceResponseDto> getAppliedRolesByStatus(
        final Member member,
        final ApplyStatus applyStatus,
        final Pageable pageable
    );

    void deleteApplicationRequestMember(
        final Member member,
        final Long applicationRequestMemberId
    );

    List<ApplicationRequestCompanyReadServiceResponseDto> getAppliedMembersByRole(
        final Company company,
        final long roleId,
        final Pageable pageable
    );

    void updateStatus(
        final Company company,
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );
    void createAttendanceManagementIfApproved(
        final Long applicationRequestMemberId,
        final ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto
    );

}
