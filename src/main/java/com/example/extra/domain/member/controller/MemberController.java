package com.example.extra.domain.member.controller;

import com.example.extra.domain.member.dto.controller.MemberLoginControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberSignUpControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberLoginServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberLoginServiceResponseDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.mapper.dto.MemberDtoMapper;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.mapper.dto.TattooDtoMapper;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;
    private final TattooDtoMapper tattooDtoMapper;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
        @Valid @RequestBody MemberSignUpControllerRequestDto requestDto
    ) {
        MemberCreateServiceRequestDto memberCreateServiceRequestDto =
            memberDtoMapper.toMemberCreateServiceRequestDto(
                requestDto.memberCreate());
        TattooCreateServiceRequestDto tattooCreateServiceRequestDto =
            tattooDtoMapper.toTattooCreateServiceRequestDto(
                requestDto.tattooCreate());

        memberService.signup(
            memberCreateServiceRequestDto,
            tattooCreateServiceRequestDto
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
        @Valid @RequestBody MemberLoginControllerRequestDto controllerRequestDto
    ) {
        MemberLoginServiceRequestDto serviceRequestDto =
            memberDtoMapper.toMemberLoginServiceRequestDto(controllerRequestDto);

        MemberLoginServiceResponseDto serviceResponseDto =
            memberService.login(serviceRequestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(
                AUTHORIZATION_HEADER,
                serviceResponseDto.token()
            )
            .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @NotNull HttpServletRequest request
    ) throws ServletException, IOException {
        memberService.logout(userDetails, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("")
    public ResponseEntity<MemberReadServiceResponseDto> readUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @NotNull HttpServletRequest request
    ) {
        MemberReadServiceResponseDto serviceResponseDto =
            memberService.readUser(
                userDetails,
                request
            );
        return ResponseEntity.status(HttpStatus.OK).body(serviceResponseDto);
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity<?> deleteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @NotNull HttpServletRequest request
    ) {
        memberService.deleteUser(
            userDetails,
            request
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}