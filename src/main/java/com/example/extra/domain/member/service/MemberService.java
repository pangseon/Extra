package com.example.extra.domain.member.service;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberLoginServiceResponseDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;

public interface MemberService {

    void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );

    MemberLoginServiceResponseDto login(
        final MemberLoginServiceRequestDto memberLoginServiceRequestDto
    );

    MemberReadServiceResponseDto readUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    );
}
