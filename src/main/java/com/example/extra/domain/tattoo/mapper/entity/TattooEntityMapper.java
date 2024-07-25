package com.example.extra.domain.tattoo.mapper.entity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface TattooEntityMapper {

    Tattoo toTattoo(
        MemberCreateServiceRequestDto memberCreateServiceRequestDto);

}
