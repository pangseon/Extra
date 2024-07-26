package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberCreateServiceResponseDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TattooRepository tattooRepository;
    private final MemberEntityMapper memberEntityMapper;
    private final TattooEntityMapper tattooEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberCreateServiceResponseDto signup(
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
        memberRepository.save(member);

        MemberCreateServiceResponseDto memberCreateServiceResponseDto
            = new MemberCreateServiceResponseDto(
            memberRepository.findById(member.getId()).get().getId()
        );

        return memberCreateServiceResponseDto;
    }
}
