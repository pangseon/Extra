package com.example.extra.domain.tattoo.mapper.service;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TattooEntityMapper {

    Tattoo toTattoo(TattooCreateServiceRequestDto tattooCreateServiceRequestDto);
}
