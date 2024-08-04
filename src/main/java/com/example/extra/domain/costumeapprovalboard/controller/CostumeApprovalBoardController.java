package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;
    // 업체가 특정 게시글 조회
    @GetMapping("/{costumeApprovalBoardId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromMember(
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
}
