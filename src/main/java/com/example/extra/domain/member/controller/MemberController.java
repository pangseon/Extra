package com.example.extra.domain.member.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.member.dto.controller.MemberCreateControllerRequestDto;
import com.example.extra.domain.member.dto.controller.MemberUpdateControllerRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.mapper.dto.MemberDtoMapper;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.global.exception.CustomValidationException;
import com.example.extra.global.exception.dto.FieldErrorResponseDto;
import com.example.extra.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Member", description = "보조 출연자 정보 API")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    // service
    private final MemberService memberService;

    // mapper
    private final MemberDtoMapper memberDtoMapper;

    @Operation(
        summary = "보조 출연자 회원 가입",
        description = "회원 가입 [과정 2-1] : account id와 보조 출연자 정보를 입력해서 회원 가입 진행"
    )
    @ApiResponse(description = "보조 출연자 회원 가입 성공")
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

    @Operation(
        summary = "보조 출연자 정보 단건 조회",
        description = "보조 출연자 정보 단건 조회하기. 보조 출연자가 자신의 정보를 보고 싶을 때 사용합니다."
    )
    @ApiResponse(description = "보조 출연자 단건 조회 성공", content = @Content(schema = @Schema(implementation = MemberReadServiceResponseDto.class)))
    @GetMapping("")
    public ResponseEntity<MemberReadServiceResponseDto> readOnce(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        MemberReadServiceResponseDto serviceResponseDto =
            memberService.readOnce(userDetails.getAccount());

        return ResponseEntity
            .status(OK)
            .body(serviceResponseDto);
    }

    @Operation(
        summary = "보조 출연자 정보 수정",
        description = "보조 출연자 정보 수정하기. 보조 출연자가 자신의 정보를 수정할 때 사용합니다. "
            + "isImageChange 값을 사용해서 이미지 변경 여부를 파악합니다. 수정한 회원 정보, 이미지를 request part로 받습니다."
    )
    @PutMapping("")
    public ResponseEntity<Void> update(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestPart(name = "member") MemberUpdateControllerRequestDto controllerRequestDto,
        @Nullable @RequestPart(name = "image") MultipartFile multipartFile
    ) {
        if(controllerRequestDto.isImageDelete() || controllerRequestDto.isImageUpdate()){
            if (ObjectUtils.isEmpty(controllerRequestDto.imageUrl())){
                throw new CustomValidationException(FieldErrorResponseDto.builder()
                        .name("image")
                        .message("프로필 사진 수정 혹은 삭제 요청 시 기존 이미지 url은 필수 입니다.")
                    .build());
            }
        }
        if (controllerRequestDto.isImageUpdate() && ObjectUtils.isEmpty(multipartFile)){
            throw new CustomValidationException(FieldErrorResponseDto.builder()
                    .name("image")
                    .message("프로필 사진 수정 요청 시 새 이미지 파일은 필수 입니다.")
                .build());
        }
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

    @Operation(
        summary = "보조 출연자 정보 삭제",
        description = "보조 출연자 정보 삭제하기. 보조 출연자와 연관된 정보도 함께 전부 삭제됩니다."
    )
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        memberService.delete(userDetails.getAccount());

        return ResponseEntity
            .status(OK)
            .build();
    }
}