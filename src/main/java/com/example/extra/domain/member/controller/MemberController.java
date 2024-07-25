package com.example.extra.domain.member.controller;

import com.example.extra.domain.member.dto.controller.MemberCreateControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberCreateServiceResponseDto;
import com.example.extra.domain.member.mapper.dto.MemberDtoMapper;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.mapper.dto.TattooDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;
    private final TattooDtoMapper tattooDtoMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestPart(name = "memberCreateControllerRequestDto") MemberCreateControllerRequestDto memberCreateControllerRequestDto,
        @Valid @RequestPart(name = "tattooCreateControllerRequestDto") TattooCreateControllerRequestDto tattooCreateControllerRequestDto
    ) {
        MemberCreateServiceRequestDto memberCreateServiceRequestDto
            = memberDtoMapper.toMemberCreateServiceRequestDto(memberCreateControllerRequestDto);
        TattooCreateServiceRequestDto tattooCreateServiceRequestDto
            = tattooDtoMapper.toTattooCreateServiceRequestDto(tattooCreateControllerRequestDto);
        MemberCreateServiceResponseDto memberCreateServiceResponseDto
            = memberService.signup(
            memberCreateServiceRequestDto,
            tattooCreateServiceRequestDto
        );
        return ResponseEntity.ok(memberCreateServiceResponseDto);
    }

}