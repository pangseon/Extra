package com.example.extra.domain.applicationrequest.mapper.dto;


import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestMemberUpdateControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ApplicationRequestDtoMapper {

    ApplicationRequestMemberUpdateServiceRequestDto  toApplicationRequestMemberUpdateServiceRequestDto(
        ApplicationRequestMemberUpdateControllerRequestDto controllerRequestDto);
}
