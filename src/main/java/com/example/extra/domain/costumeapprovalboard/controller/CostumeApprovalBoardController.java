package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.global.security.UserDetailsImpl;
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
    // 출연자 본인 역할에 대해 의상 컨펌 삭제
    @DeleteMapping("/member/roles/{roleId}")
    public ResponseEntity<?> deleteCostumeApprovalBoardFromMember(
        @PathVariable Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        costumeApprovalBoardService.deleteCostumeApprovalBoardByMember(
            userDetails.getMember(),
            roleId
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}