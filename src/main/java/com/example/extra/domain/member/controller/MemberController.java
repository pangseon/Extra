package com.example.extra.domain.member.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.member.dto.controller.MemberCreateControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberSignUpControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberUpdateControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.mapper.dto.MemberDtoMapper;
import com.example.extra.domain.member.service.MemberService;
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

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @Valid @RequestBody MemberCreateControllerRequestDto controllerRequestDto
    ) {
        MemberCreateServiceRequestDto serviceRequestDto =
            memberDtoMapper.toMemberCreateServiceRequestDto(controllerRequestDto);
        memberService.signup(serviceRequestDto);
        return ResponseEntity
            .status(CREATED)
            .build();
    }

    @GetMapping("")
    public ResponseEntity<MemberReadServiceResponseDto> readOnce(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @NotNull HttpServletRequest request
    ) {
        MemberReadServiceResponseDto serviceResponseDto =
            memberService.readOnce(
                userDetails.getAccount(),
                request
            );

        return ResponseEntity
            .status(OK)
            .body(serviceResponseDto);
    }

    @PutMapping("")
    public ResponseEntity<Void> update(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Nullable @RequestPart(name = "member") MemberUpdateControllerRequestDto controllerRequestDto,
        @Nullable @RequestPart(name = "image") MultipartFile multipartFile
    ) throws IOException {
        MemberUpdateServiceRequestDto serviceRequestDto =
            memberDtoMapper.toMemberUpdateServiceRequestDto(controllerRequestDto);

        memberService.update(
            userDetails.getAccount(),
            serviceRequestDto,
            multipartFile
        );

        return ResponseEntity
            .status(OK)
            .build();
    }

    @DeleteMapping("")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.delete(userDetails.getAccount());

        return ResponseEntity
            .status(OK)
            .build();
    }
}