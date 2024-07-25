package com.example.extra.domain.applicationrequest.mapper.dto;

import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestCreateControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestDeleteControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCreateServiceRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestDeleteServiceRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ApplicationRequestDtoMapper {
    ApplicationRequestCreateServiceRequestDto toApplicationRequestCreateServiceRequestDto(
        ApplicationRequestCreateControllerRequestDto controllerRequestDto
    );
    ApplicationRequestDeleteServiceRequestDto toApplicationRequestDeleteServiceRequestDto(
        ApplicationRequestDeleteControllerRequestDto controllerRequestDto
    );

}
