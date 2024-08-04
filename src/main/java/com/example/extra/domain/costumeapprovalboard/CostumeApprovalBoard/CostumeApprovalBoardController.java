package com.example.extra.domain.costumeapprovalboard.CostumeApprovalBoard;

import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainCreateRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;

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
            CostumeApprovalBoardCreateServiceDto.builder()
                .image_explain(costumeApprovalBoardExplainCreateRequestDto.image_explain())
                .multipartFile(multipartFile)
                .build();

        costumeApprovalBoardService.createCostumeApprovalBoard(
            roleId,
            member,
            costumeApprovalBoardCreateServiceDto
        );

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }
}
