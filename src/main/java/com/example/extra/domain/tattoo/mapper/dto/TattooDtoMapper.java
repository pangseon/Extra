package com.example.extra.domain.tattoo.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TattooDtoMapper {

    TattooCreateServiceRequestDto toTattooCreateServiceRequestDto(
        TattooCreateControllerRequestDto tattooCreateControllerRequestDto
    );

}
