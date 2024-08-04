package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.global.security.UserDetailsImpl;
import java.util.List;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;
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
    ){
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
    ){
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
    ){
        if(Objects.nonNull(userDetails.getMember())){
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
}