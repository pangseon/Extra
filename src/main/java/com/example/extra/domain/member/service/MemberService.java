package com.example.extra.domain.member.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface MemberService {

    void signup(final MemberCreateServiceRequestDto memberCreateServiceRequestDto);

    MemberReadServiceResponseDto readOnce(
        final Account account,
        final HttpServletRequest request
    );

    void delete(final Account account);

    void update(
        final Account account,
        final MemberUpdateServiceRequestDto memberUpdateServiceRequestDto,
        final MultipartFile multipartFile
    ) throws IOException;
}
