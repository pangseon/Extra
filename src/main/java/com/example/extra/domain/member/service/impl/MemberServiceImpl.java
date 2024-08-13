package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.repository.CompanyRepository;
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
    private final CompanyRepository companyRepository;

    // mapper
    private final MemberEntityMapper memberEntityMapper;

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
        companyRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new CompanyException(CompanyErrorCode.ALREADY_EXIST_EMAIL);
            });

        Member member = memberEntityMapper.toMember(memberCreateServiceRequestDto);
        Tattoo tattoo = tattooRepository.findByTattooCreateServiceRequestDto(tattooCreateServiceRequestDto)
            .orElseThrow(()-> new MemberException(MemberErrorCode.NOT_FOUND_TATTOO));

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
        String accessToken = jwtUtil.createToken(
            member.getEmail(),
            member.getUserRole()
        );
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("access token: " + accessToken);
        log.info("refresh token: " + refreshToken);

        member.updateRefreshToken(jwtUtil.substringToken(refreshToken));

        refreshTokenRepository.save(
            new RefreshToken(
                member.tokenId(),
                jwtUtil.substringToken(refreshToken)
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
        Member member = userDetails.getMember();

        RefreshToken refreshToken = refreshTokenRepository.findById(member.tokenId())
            .orElseThrow(() -> new MemberException(MemberErrorCode.UNAUTHORIZED));
        refreshTokenRepository.delete(refreshToken);

        member.deleteRefreshToken();
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberReadServiceResponseDto readUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) {
        Member member = findByEmail(userDetails.getUsername());
        Tattoo tattoo = member.getTattoo();
        return MemberReadServiceResponseDto.builder()
            .name(member.getName())
            .sex(member.getSex())
            .birthday(member.getBirthday())
            .home(member.getHome())
            .height(member.getHeight())
            .weight(member.getWeight())
            .introduction(member.getIntroduction())
            .license(member.getLicense())
            .pros(member.getPros())
            .face(tattoo.getFace())
            .chest(tattoo.getChest())
            .arm(tattoo.getArm())
            .leg(tattoo.getLeg())
            .shoulder(tattoo.getShoulder())
            .back(tattoo.getBack())
            .hand(tattoo.getHand())
            .feet(tattoo.getFeet())
            .build();
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

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    // 테스트용 유저 받아오기
    private Member getMemberForTest() {
        return memberRepository.findById(1L)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
