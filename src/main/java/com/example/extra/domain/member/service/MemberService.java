package com.example.extra.domain.member.service;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    void signup(
        HttpServletResponse res,
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );
}
