package com.example.extra.domain.member.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    );

    MemberReadServiceResponseDto readUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    );

    void deleteUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    );

    void update(
        Account account,
        MemberUpdateServiceRequestDto memberUpdateServiceRequestDto,
        TattooCreateServiceRequestDto tattooCreateServiceRequestDto,
        MultipartFile multipartFile
    ) throws IOException;
}
