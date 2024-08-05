package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainCreateRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainUpdateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.mapper.dto.CostumeApprovalBoardDtoMapper;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;
    private final CostumeApprovalBoardDtoMapper costumeApprovalBoardDtoMapper;


    // 업체가 의상 승인 게시글 목록 조회
    @GetMapping("/jobposts/{jobPostId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromCompany(
        @PathVariable Long jobPostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        List<CostumeApprovalBoardCompanyReadServiceResponseDto> costumeApprovalBoardCompanyReadServiceResponseDtoList =
            costumeApprovalBoardService.getCostumeApprovalBoardForCompany(
                userDetails.getCompany(),
                jobPostId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(costumeApprovalBoardCompanyReadServiceResponseDtoList);
    }

    // 업체가 특정 게시글 조회
    @GetMapping("/{costumeApprovalBoardId}")
    public ResponseEntity<?> readCostumeApprovalBoardDetailFromCompany(
        @PathVariable Long costumeApprovalBoardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CostumeApprovalBoardCompanyReadDetailServiceResponseDto costumeApprovalBoardCompanyReadDetailServiceResponseDto =
            costumeApprovalBoardService.getCostumeApprovalBoardDetailForCompany(
                userDetails.getCompany(),
                costumeApprovalBoardId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(costumeApprovalBoardCompanyReadDetailServiceResponseDto);
    }

    // 출연자 본인 역할에 대해 의상 컨펌 조회
    @GetMapping("/member/roles/{roleId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromMember(
        @PathVariable Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CostumeApprovalBoardMemberReadServiceResponseDto costumeApprovalBoardMemberReadServiceResponseDto =
            costumeApprovalBoardService.getCostumeApprovalBoardForMember(
                userDetails.getMember(),
                roleId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(costumeApprovalBoardMemberReadServiceResponseDto);
    }

    // TODO - 출연자의 삭제 요청과 회사의 삭제 요청 API상으로 구분할지
    // 의상 컨펌 게시글 삭제
    @DeleteMapping("/{costumeApprovalBoardId}")
    public ResponseEntity<?> deleteCostumeApprovalBoardFromMember(
        @PathVariable Long costumeApprovalBoardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (Objects.nonNull(userDetails.getMember())) {
            costumeApprovalBoardService.deleteCostumeApprovalBoardByMember(
                userDetails.getMember(),
                costumeApprovalBoardId
            );
        } else {
            costumeApprovalBoardService.deleteCostumeApprovalBoardByCompany(
                userDetails.getCompany(),
                costumeApprovalBoardId
            );
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 의상 승인 게시판 글 작성하기
     *
     * @param userDetails
     * @param roleId
     * @param costumeApprovalBoardExplainCreateRequestDto : image_explain - Nullable
     * @param multipartFile                               : image file - Not Null
     * @return
     */
    @PostMapping("/roles/{role_id}")
    public ResponseEntity<?> createCostumeApprovalBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "role_id") Long roleId,
        @RequestPart(name = "explain") CostumeApprovalBoardExplainCreateRequestDto costumeApprovalBoardExplainCreateRequestDto,
        @NotNull @RequestPart(name = "image") MultipartFile multipartFile
    ) {
        Member member = userDetails.getMember();
        CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto =
            costumeApprovalBoardDtoMapper.toCostumeApprovalBoardCreateServiceDto(
                costumeApprovalBoardExplainCreateRequestDto,
                multipartFile
            );

        costumeApprovalBoardService.createCostumeApprovalBoard(
            roleId,
            member,
            costumeApprovalBoardCreateServiceDto
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }

    /**
     * [회원] 의상 승인 게시판 글 내용 수정하기
     *
     * @param userDetails
     * @param costumeApprovalBoardId
     * @param controllerRequestDto   : image_explain - Nullable
     * @param multipartFile          : image file - Nullable
     * @return ResponseEntity - OK(200)
     */
    @PutMapping("/{costume_approval_board_id}/members")
    public ResponseEntity<?> updateCostumeApprovalBoardByMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "costume_approval_board_id") Long costumeApprovalBoardId,
        @Nullable @RequestPart(name = "explain") CostumeApprovalBoardExplainUpdateControllerRequestDto controllerRequestDto,
        @Nullable @RequestPart(name = "image") MultipartFile multipartFile
    ) {
        CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto =
            costumeApprovalBoardDtoMapper.toCostumeApprovalBoardExplainUpdateServiceRequestDto(
                controllerRequestDto,
                multipartFile
            );

        costumeApprovalBoardService.updateCostumeApprovalBoardByMember(
            costumeApprovalBoardId,
            userDetails.getMember(),
            serviceRequestDto
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}