package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberLoginServiceResponseDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.mapper.entity.MemberEntityMapper;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.mapper.entity.TattooEntityMapper;
import com.example.extra.domain.tattoo.repository.TattooRepository;
import com.example.extra.global.security.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TattooRepository tattooRepository;
    private final MemberEntityMapper memberEntityMapper;
    private final TattooEntityMapper tattooEntityMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final String ADMIN_TOKEN = "admintoken";

    @Override
    public void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    ) {
        String email = memberCreateServiceRequestDto.email();
        memberRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new MemberException(MemberErrorCode.ALREADY_EXIST_MEMBER);
            });

        Member member = memberEntityMapper.toMember(memberCreateServiceRequestDto);
        Tattoo tattoo = tattooEntityMapper.toTattoo(tattooCreateServiceRequestDto, member);

        tattooRepository.save(tattoo);

        member.updateTattoo(tattoo);
        member.encodePassword(passwordEncoder.encode(member.getPassword()));
        if (memberCreateServiceRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(memberCreateServiceRequestDto.adminToken())) {
                throw new IllegalArgumentException("관리자 암호 아님");
            }
            member.updateRole();
        }
        memberRepository.save(member);
    }

    @Override
    public MemberLoginServiceResponseDto login(
        final MemberLoginServiceRequestDto memberLoginServiceRequestDto
    ) {
        Member member = findByEmail(memberLoginServiceRequestDto.email());

        if (!passwordEncoder.matches(
            memberLoginServiceRequestDto.password(),
            member.getPassword()
        )) {
            throw new MemberException(MemberErrorCode.NOT_MATCH_PASSWORD);
        }

        String token = jwtUtil.createToken(member.getEmail(), member.getUserRole());
        log.info("jwt 토큰: " + token);
        return new MemberLoginServiceResponseDto(token);
    }

    @Override
    public MemberReadServiceResponseDto getMemberInfo(
        final Principal principal,
        final HttpServletRequest request
    ) {
        Member member = findByEmail(principal.getName());
        return memberEntityMapper.toMemberReadServiceResponseDto(member);
    }

    private Member findByEmail(String name) {
        return memberRepository.findByEmail(name)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    private Member getMemberForTest() {
        return memberRepository.findById(1L)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
