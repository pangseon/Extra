package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberCreateServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.mapper.entity.MemberEntityMapper;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.mapper.entity.TattooEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberEntityMapper memberEntityMapper;
    private final TattooEntityMapper tattooEntityMapper;

    @Override
    public MemberCreateServiceResponseDto signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    ) {
        Tattoo tattoo = tattooEntityMapper.toTattoo(memberCreateServiceRequestDto);
        Member member = memberEntityMapper.toMember(memberCreateServiceRequestDto, tattoo);

        Member signupMember = memberRepository.save(member);

        return memberEntityMapper.toMemberCreateServiceResponseDto(signupMember);
    }
}
