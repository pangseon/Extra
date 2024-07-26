package com.example.extra.domain.applicationrequest.mapper.entity;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ApplicationRequestDtoMapper {
    @Mapping(source = "id", target = "id")
    ApplicationRequestMemberReadServiceResponseDto toApplicationRequestMemberReadServiceResponseDto(Role role);

    List<ApplicationRequestMemberReadServiceResponseDto> toApplicationRequestMemberReadServiceResponseDtoList(List<Role> roleList);

    @Mapping(source = "id", target = "id")
    ApplicationRequestCompanyReadServiceResponseDto toApplicationRequestCompanyReadServiceResponseDto(Member member);
    List<ApplicationRequestCompanyReadServiceResponseDto> toApplicationRequestCompanyReadServiceResponseDtoList(List<Member> member);
}
