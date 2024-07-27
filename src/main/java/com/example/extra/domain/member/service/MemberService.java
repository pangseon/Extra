package com.example.extra.domain.member.service;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberLoginServiceResponseDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface MemberService {

    void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );

    MemberLoginServiceResponseDto login(
        final MemberLoginServiceRequestDto memberLoginServiceRequestDto
    );

    MemberReadServiceResponseDto getMemberInfo(
        final Principal principal,
        final HttpServletRequest request
    );
}
