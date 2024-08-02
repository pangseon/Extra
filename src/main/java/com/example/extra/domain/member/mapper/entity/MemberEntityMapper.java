package com.example.extra.domain.member.mapper.entity;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.tattoo.entity.Tattoo;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface MemberEntityMapper {

    Member toMember(
        MemberCreateServiceRequestDto memberCreateServiceRequestDto
    );

    MemberReadServiceResponseDto toMemberReadServiceResponseDto(
        Member member,
        Tattoo tattoo
    );
}
