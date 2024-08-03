package com.example.extra.domain.member.mapper.dto;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.member.dto.controller.MemberCreateControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberLoginControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface MemberDtoMapper {

    MemberCreateServiceRequestDto toMemberCreateServiceRequestDto(
        MemberCreateControllerRequestDto memberCreateControllerRequestDto
    );

    MemberLoginServiceRequestDto toMemberLoginServiceRequestDto(
        MemberLoginControllerRequestDto memberLoginControllerRequestDto
    );

}
