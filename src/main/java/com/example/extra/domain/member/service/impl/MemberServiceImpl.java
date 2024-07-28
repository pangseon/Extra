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
import com.example.extra.global.security.UserDetailsImpl;
import com.example.extra.global.security.repository.RefreshTokenRepository;
import com.example.extra.global.security.token.RefreshToken;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TattooRepository tattooRepository;
    private final MemberEntityMapper memberEntityMapper;
    private final TattooEntityMapper tattooEntityMapper;

    // security
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    private final String ADMIN_TOKEN = "admintoken";

    @Override
    @Transactional
    public void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    ) {
        // 이메일 중복 검사
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

        // role 변경 (ROLE_USER -> ROLE_ADMIN)
        if (memberCreateServiceRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(memberCreateServiceRequestDto.adminToken())) {
                throw new IllegalArgumentException("관리자 암호 아님");
            }
            member.updateRole();
        }

        memberRepository.save(member);
    }

    @Override
    @Transactional
    public MemberLoginServiceResponseDto login(
        final MemberLoginServiceRequestDto memberLoginServiceRequestDto
    ) {
        Member member = findByEmail(memberLoginServiceRequestDto.email());

        if (!passwordEncoder.matches(
            memberLoginServiceRequestDto.password(),
            member.getPassword()
        )) {
            throw new MemberException(MemberErrorCode.INVALID_PASSWORD);
        }

        // jwt 토큰 생성
        String accessToken = jwtUtil.createToken(member.getEmail(), member.getUserRole());
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("access token: " + accessToken);
        log.info("refresh token: " + refreshToken);

        refreshTokenRepository.save(
            new RefreshToken(
                member.getId(),
                refreshToken,
                accessToken
            )
        );

        return new MemberLoginServiceResponseDto(accessToken);
    }

    @Override
    @Transactional
    public void logout(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) throws ServletException, IOException {
        String token = jwtUtil.getTokenFromRequest(request);

        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(token)
            .orElseThrow(() -> new MemberException(MemberErrorCode.UNAUTHORIZED));
        refreshTokenRepository.delete(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberReadServiceResponseDto readUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) {
        Member member = findByEmail(userDetails.getUsername());
        return memberEntityMapper.toMemberReadServiceResponseDto(
            member,
            member.getTattoo()
        );
    }

    @Override
    @Transactional
    public void deleteUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) {
        Member member = findByEmail(userDetails.getUsername());
        memberRepository.delete(member);
    }

    private Member findByEmail(String name) {
        return memberRepository.findByEmail(name)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    // 테스트용 유저 받아오기
    private Member getMemberForTest() {
        return memberRepository.findById(1L)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
