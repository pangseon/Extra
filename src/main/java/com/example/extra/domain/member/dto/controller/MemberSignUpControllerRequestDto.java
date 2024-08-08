package com.example.extra.domain.member.dto.controller;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import lombok.Builder;

@Builder
public record MemberSignUpControllerRequestDto(
    MemberCreateControllerRequestDto memberCreate,
    TattooCreateControllerRequestDto tattooCreate
) {

}
