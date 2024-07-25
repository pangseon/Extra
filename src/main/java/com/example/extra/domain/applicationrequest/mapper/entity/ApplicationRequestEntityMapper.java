package com.example.extra.domain.applicationrequest.mapper.entity;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCreateServiceRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestDeleteServiceRequestDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
@Mapper(componentModel = ComponentModel.SPRING)
public interface ApplicationRequestEntityMapper {
    // 업체가 출연자에게 출연 요청 하는 경우
    ApplicationRequestCompany toApplicationRequestCompany(
        ApplicationRequestCreateServiceRequestDto applicationRequestCreateServiceRequestDto,
        Member member,
        Role role
    );
    // 출연자가 지원 하는 경우
    ApplicationRequestMember toApplicationRequestMemberFromCreateRequest(
        ApplicationRequestCreateServiceRequestDto applicationRequestCreateServiceRequestDto,
        Member member,
        Role role
    );
    // 출연자가 지원 취소 하는 경우
    ApplicationRequestMember toApplicationRequestMemberFromDeleteRequest(
        ApplicationRequestDeleteServiceRequestDto applicationRequestDeleteServiceRequestDto,
        Member member,
        Role role
    );
}
