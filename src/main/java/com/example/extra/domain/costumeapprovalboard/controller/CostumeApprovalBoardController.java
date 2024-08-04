package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.global.security.UserDetailsImpl;
import java.util.List;
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
    // 업체가 의상 승인 게시글 목록 조회
    @GetMapping("/jobposts/{jobPostId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromMember(
        @PathVariable Long jobPostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        List<CostumeApprovalBoardCompanyReadServiceResponseDto> costumeApprovalBoardCompanyReadServiceResponseDtoList =
            costumeApprovalBoardService.getCostumeApprovalBoardForCompany(
                userDetails.getCompany(),
                jobPostId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(costumeApprovalBoardCompanyReadServiceResponseDtoList);
    }
}