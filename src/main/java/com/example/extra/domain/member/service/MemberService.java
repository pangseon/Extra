package com.example.extra.domain.member.service;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import jakarta.servlet.http.HttpServletResponse;

public interface MemberService {

    void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );

    void login(
        HttpServletResponse res,
        final MemberLoginServiceRequestDto memberLoginServiceRequestDto
    );
}
