package com.example.extra.domain.applicationrequest.controller;

import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestMemberUpdateControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.mapper.dto.ApplicationRequestDtoMapper;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.exception.dto.CustomExceptionResponseDto;
import com.example.extra.global.security.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/application-request")
@RestController
public class ApplicationRequestMemberController {

    private final ApplicationRequestMemberService applicationRequestMemberService;
    private final ApplicationRequestDtoMapper applicationRequestDtoMapper;

    // 사용자가 지원한 역할들과 지원 상태 확인
    @GetMapping("/member/roles")
    public ResponseEntity<?> readAllApplicationRequestMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PageableDefault(size = 5, sort = "createdAt", direction = Direction.DESC) @Parameter(hidden = true) Pageable pageable,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Integer month
    ) {
        List<ApplicationRequestMemberReadServiceResponseDto> serviceResponseDtoList =
            applicationRequestMemberService.getAppliedRoles(
                userDetails.getAccount(),
                year,
                month,
                pageable
            );

        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDtoList);
    }

    // 사용자가 지원한 역할들 중 특정 상태인 것들만 확인
    @GetMapping("/member/roles/{status}")
    public ResponseEntity<?> readAllApplicationRequestMemberByStatus(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "status") String applyStatusString,
        @PageableDefault(size = 5, sort = "createdAt", direction = Direction.DESC) @Parameter(hidden = true) Pageable pageable
    ) {
        ApplyStatus applyStatus = ApplyStatus.fromString(applyStatusString);
        if (applyStatus == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CustomExceptionResponseDto.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .name("applyStatus")
                    .message("유효하지 않은 apply status입니다.")
                    .build());

        }
        List<ApplicationRequestMemberReadServiceResponseDto> serviceResponseDtoList =
            applicationRequestMemberService.getAppliedRolesByStatus(
                userDetails.getAccount(),
                applyStatus,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDtoList);
    }

    // 사용자가 특정 역할에 지원
    @PostMapping("/member/roles/{roleId}")
    public ResponseEntity<?> createApplicationRequestMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "roleId") Long roleId
    ) {
        applicationRequestMemberService.createApplicationRequestMember(
            userDetails.getAccount(),
            roleId
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 특정 역할에 지원 취소
    @DeleteMapping("/member/application-requests/{applicationRequestId}")
    public ResponseEntity<?> deleteApplicationRequestMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "applicationRequestId") Long applicationRequestId
    ) {
        applicationRequestMemberService.deleteApplicationRequestMember(
            userDetails.getAccount(),
            applicationRequestId
        );
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 해당 역할에 지원한 출연자들(지원현황 화면)
    @GetMapping("/company/roles/{roleId}/members")
    public ResponseEntity<?> readAllApplicationRequestMemberByStatus(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "roleId") Long roleId,
        @PageableDefault(size = 5, sort = "createdAt", direction = Direction.DESC) @Parameter(hidden = true) Pageable pageable
    ) {
        List<ApplicationRequestCompanyReadServiceResponseDto> serviceResponseDtoList =
            applicationRequestMemberService.getAppliedMembersByRole(
                userDetails.getAccount(),
                roleId,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(serviceResponseDtoList);
    }

    // 해당 역할에 지원한 출연자들 개별 조회
    @GetMapping("/company/application-requests/{applicationRequest_id}")
    public ResponseEntity<MemberReadServiceResponseDto> readOnceApplicationRequestMember(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "applicationRequest_id") Long applicationRequestId
    ) {
        MemberReadServiceResponseDto serviceResponseDto =
            applicationRequestMemberService.readOnceApplicationRequestMember(
                userDetails.getAccount(),
                applicationRequestId
            );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(serviceResponseDto);
    }

    // 요청 승인 및 거절(지원현황 화면)
    @PutMapping("/company/application-requests/{applicationRequestId}")
    public ResponseEntity<?> updateApplicationRequestMemberStatusToRejected(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "applicationRequestId") Long applicationRequestId,
        @Valid @RequestBody ApplicationRequestMemberUpdateControllerRequestDto controllerRequestDto
    ) {
        ApplicationRequestMemberUpdateServiceRequestDto serviceRequestDto =
            applicationRequestDtoMapper.toApplicationRequestMemberUpdateServiceRequestDto(
                controllerRequestDto
            );
        applicationRequestMemberService.updateStatus(
            userDetails.getAccount(),
            applicationRequestId,
            serviceRequestDto
        );
        if (serviceRequestDto.applyStatus() == ApplyStatus.APPROVED) {
            applicationRequestMemberService.createAttendanceManagementIfApproved(
                applicationRequestId,
                serviceRequestDto
            );
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}