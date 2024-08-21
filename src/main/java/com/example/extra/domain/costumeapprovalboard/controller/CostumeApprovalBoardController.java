package com.example.extra.domain.costumeapprovalboard.controller;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardApplyStatusUpdateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardExplainCreateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.controller.CostumeApprovalBoardUpdateControllerRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardApplyStatusUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardExplainCreateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardMemberReadUrlServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.mapper.dto.CostumeApprovalBoardDtoMapper;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.exception.CustomValidationException;
import com.example.extra.global.exception.dto.FieldErrorResponseDto;
import com.example.extra.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/costumeapprovalboards")
public class CostumeApprovalBoardController {

    private final CostumeApprovalBoardService costumeApprovalBoardService;
    private final CostumeApprovalBoardDtoMapper costumeApprovalBoardDtoMapper;

    private static final long MAX_FILE_SIZE = 30 * 1024 * 1024; // 30 MB (최신 삼성폰 이미지 크기 30MB. 아이폰 15MB)

    // 업체가 의상 승인 게시글 목록 조회
    @GetMapping("/roles/{roleId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromCompany(
        @PathVariable Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestParam(required = false) String name,
        @PageableDefault(size = 5, sort = "createdAt", direction = Direction.DESC) @Parameter(hidden = true) Pageable pageable
    ) {
        // 한글(\uAC00-\uD7AF), 알파벳([a-zA-Z])만 가능. 1자에서 10자까지 허용됨
        if (name != null && !name.matches("^[\\uAC00-\\uD7AFa-zA-Z]{1,10}$")) {
            throw new CustomValidationException(FieldErrorResponseDto.builder()
                    .name("name")
                    .message("name은 한글 또는 영어 알파벳으로만 구성되어야 하며, 길이는 10자 이하이어야 합니다.")
                .build());
        }
        List<CostumeApprovalBoardCompanyReadServiceResponseDto> serviceResponseDtoList =
            costumeApprovalBoardService.getCostumeApprovalBoardForCompany(
                userDetails.getAccount(),
                roleId,
                name,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDtoList);
    }

    // 업체가 특정 게시글 조회
    @GetMapping("/{costumeApprovalBoardId}")
    public ResponseEntity<?> readCostumeApprovalBoardDetailFromCompany(
        @PathVariable Long costumeApprovalBoardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CostumeApprovalBoardCompanyReadDetailServiceResponseDto serviceResponseDto =
            costumeApprovalBoardService.getCostumeApprovalBoardDetailForCompany(
                userDetails.getAccount(),
                costumeApprovalBoardId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDto);
    }

    // 출연자 본인 역할에 대해 의상 컨펌 조회
    @GetMapping("/member/roles/{roleId}")
    public ResponseEntity<?> readCostumeApprovalBoardFromMember(
        @PathVariable Long roleId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CostumeApprovalBoardMemberReadServiceResponseDto serviceResponseDto =
            costumeApprovalBoardService.getCostumeApprovalBoardForMember(
                userDetails.getAccount(),
                roleId
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDto);
    }

    // TODO - 출연자의 삭제 요청과 회사의 삭제 요청 API상으로 구분할지
    // 의상 컨펌 게시글 삭제
    @DeleteMapping("/{costumeApprovalBoardId}")
    public ResponseEntity<?> deleteCostumeApprovalBoardFromMember(
        @PathVariable Long costumeApprovalBoardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if (Objects.nonNull(userDetails.getAccount())) {
            costumeApprovalBoardService.deleteCostumeApprovalBoardByMember(
                userDetails.getAccount(),
                costumeApprovalBoardId
            );
        } else {
            costumeApprovalBoardService.deleteCostumeApprovalBoardByCompany(
                userDetails.getAccount(),
                costumeApprovalBoardId
            );
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 의상 승인 게시글 작성(이미지 등록) 시 해당 api로 요청을 보내면
     * 유효성 검증 후 S3에 PUT 요청을 보낼 수 있는 url을 반환함
     * 해당 url을 받은 프론트가 url에 put 요청을 하면 S3에 저장됨
     * @param userDetails
     * @param roleId
     * @return
     */
    @GetMapping("/member/roles/{role_id}/url")
    public ResponseEntity<?> getPresignedUrlForCostumeImage(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "role_id") Long roleId
    ){
        Account account = userDetails.getAccount();
        CostumeApprovalBoardMemberReadUrlServiceResponseDto serviceResponseDto =
            costumeApprovalBoardService.getPresignedUrl(account, roleId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDto);
    }

    /**
     * 의상 승인 게시판 글 작성하기
     *
     * @param userDetails
     * @param roleId
     * @param controllerRequestDto : image_explain - Nullable
     * @param multipartFile        : image file - Not Null
     * @return
     */
    @PostMapping("/roles/{role_id}")
    public ResponseEntity<?> createCostumeApprovalBoard(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "role_id") Long roleId,
        @RequestPart(name = "explain") CostumeApprovalBoardExplainCreateControllerRequestDto controllerRequestDto,
        @RequestPart(name = "image") MultipartFile multipartFile
    ) {
        // 이미지 필수
        if (ObjectUtils.isEmpty(multipartFile)) {
            throw new CustomValidationException(FieldErrorResponseDto.builder()
                    .name("image")
                    .message("이미지 파일은 필수입니다.")
                .build());
        }
        // 이미지 검증
        validateImageFileIfExists(multipartFile);

        Account account = userDetails.getAccount();
        CostumeApprovalBoardExplainCreateServiceRequestDto serviceRequestDto =
            costumeApprovalBoardDtoMapper.toCostumeApprovalBoardExplainCreateServiceRequestDto(
                controllerRequestDto
            );

        costumeApprovalBoardService.createCostumeApprovalBoard(
            roleId,
            account,
            serviceRequestDto,
            multipartFile
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
        @Valid @RequestPart(name = "explain") CostumeApprovalBoardUpdateControllerRequestDto controllerRequestDto,
        @Nullable @RequestPart(name = "image") MultipartFile multipartFile
    ){
        if (controllerRequestDto.isImageUpdate()) {
            if (ObjectUtils.isEmpty(multipartFile) || ObjectUtils.isEmpty(controllerRequestDto.imageUrl())) {
                throw new CustomValidationException(FieldErrorResponseDto.builder()
                        .name("image")
                        .message("의상 이미지에 대한 수정 요청 시 기존 이미지 url 및 새 이미지 파일은 필수입니다.")
                    .build());
            }
            validateImageFileIfExists(multipartFile);
        }

        CostumeApprovalBoardUpdateServiceRequestDto serviceRequestDto =
            costumeApprovalBoardDtoMapper.toCostumeApprovalBoardUpdateServiceRequestDto(
                controllerRequestDto
            );

        costumeApprovalBoardService.updateCostumeApprovalBoardByMember(
            costumeApprovalBoardId,
            userDetails.getAccount(),
            serviceRequestDto,
            multipartFile
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    /**
     * [회사] 의상 승인 글 승인하기
     *
     * @param userDetails
     * @param costumeApprovalBoardId
     * @return
     */
    @PutMapping("/{costumeApprovalBoardId}/companies")
    public ResponseEntity<?> updateCostumeApprovalBoardByCompany(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long costumeApprovalBoardId,
        @Valid @RequestBody CostumeApprovalBoardApplyStatusUpdateControllerRequestDto controllerRequestDto
    ) {

        CostumeApprovalBoardApplyStatusUpdateServiceRequestDto serviceRequestDto =
            CostumeApprovalBoardApplyStatusUpdateServiceRequestDto.builder()
                    .costumeApprove(ApplyStatus.fromString(controllerRequestDto.costumeApprove()))
                .build();

        costumeApprovalBoardService.updateCostumeApprovalBoardByCompany(
            userDetails.getAccount(),
            costumeApprovalBoardId,
            serviceRequestDto
        );

        return ResponseEntity
            .status(HttpStatus.OK)
            .build();

    }
    private void validateImageFileIfExists(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();
        // 파일 타입 검증
        if (!("image/jpeg".equals(contentType) || "image/png".equals(contentType))) {
            throw new CustomValidationException(FieldErrorResponseDto.builder()
                    .name("image")
                    .message("image/jpeg, image/png contentType만 허용됩니다.")
                .build());
        }
        // 이미지 파일 크기 검증
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            String errorMessage = String.format(
                "이미지 파일 크기는 %dMB를 넘어서는 안됩니다.",
                MAX_FILE_SIZE / (1024 * 1024)
            );
            throw new CustomValidationException(FieldErrorResponseDto.builder()
                    .name("image")
                    .message(errorMessage)
                .build());
        }
    }
}