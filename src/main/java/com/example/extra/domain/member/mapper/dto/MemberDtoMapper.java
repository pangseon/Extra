package com.example.extra.domain.member.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.member.dto.controller.MemberCreateControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberUpdateControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface MemberDtoMapper {

    MemberCreateServiceRequestDto toMemberCreateServiceRequestDto(
        MemberCreateControllerRequestDto memberCreateControllerRequestDto
    );

    MemberUpdateServiceRequestDto toMemberUpdateServiceRequestDto(
        MemberUpdateControllerRequestDto controllerRequestDto
    );
}
