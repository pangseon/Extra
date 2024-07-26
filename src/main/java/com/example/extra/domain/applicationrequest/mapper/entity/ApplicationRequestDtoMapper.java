package com.example.extra.domain.applicationrequest.mapper.entity;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ApplicationRequestDtoMapper {
    ApplicationRequestMemberReadServiceResponseDto toApplicationRequestMemberReadServiceResponseDto(Role role);

    List<ApplicationRequestMemberReadServiceResponseDto> toApplicationRequestMemberReadServiceResponseDtoList(List<Role> role);

}
