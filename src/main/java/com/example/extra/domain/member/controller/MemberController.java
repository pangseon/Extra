package com.example.extra.domain.member.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.member.dto.controller.MemberSignUpControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberUpdateControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.mapper.dto.MemberDtoMapper;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.mapper.dto.TattooDtoMapper;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;
    private final TattooDtoMapper tattooDtoMapper;
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
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

        return ResponseEntity
            .status(CREATED)
            .build();
    }

    @GetMapping("")
    public ResponseEntity<MemberReadServiceResponseDto> readOnce(
        @AuthenticationPrincipal Account account,
        @NotNull HttpServletRequest request
    ) {
        MemberReadServiceResponseDto serviceResponseDto =
            memberService.readOnce(
                account,
                request
            );

        return ResponseEntity
            .status(OK)
            .body(serviceResponseDto);
    }

    @PutMapping("")
    public ResponseEntity<Void> update(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Nullable @RequestPart(name = "member") MemberUpdateControllerRequestDto memberUpdateControllerRequestDto,
        @Nullable @RequestPart(name = "tattoo") TattooCreateControllerRequestDto tattooCreateControllerRequestDto,
        @Nullable @RequestPart(name = "image") MultipartFile multipartFile
    ) throws IOException {
        MemberUpdateServiceRequestDto memberServiceRequestDto =
            memberUpdateControllerRequestDto == null ?
                null :
                memberDtoMapper.toMemberUpdateServiceRequestDto(memberUpdateControllerRequestDto);

        TattooCreateServiceRequestDto tattooServiceRequestDto =
            tattooCreateControllerRequestDto == null ?
                null :
                tattooDtoMapper.toTattooCreateServiceRequestDto(tattooCreateControllerRequestDto);

        memberService.update(
            userDetails.getAccount(),
            memberServiceRequestDto,
            tattooServiceRequestDto,
            multipartFile
        );

        return ResponseEntity
            .status(OK)
            .build();
    }

    @DeleteMapping("/{member_id}")
    public ResponseEntity<Void> deleteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @NotNull HttpServletRequest request
    ) {
        memberService.deleteUser(
            userDetails,
            request
        );

        return ResponseEntity
            .status(OK)
            .build();
    }
}