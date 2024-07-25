package com.example.extra.domain.member.service;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberCreateServiceResponseDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;

public interface MemberService {

    MemberCreateServiceResponseDto signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );
}
