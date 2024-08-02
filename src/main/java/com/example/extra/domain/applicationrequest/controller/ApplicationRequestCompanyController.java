package com.example.extra.domain.applicationrequest.controller;

import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestMemberUpdateControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberUpdateServiceRequestDto;
import com.example.extra.domain.applicationrequest.mapper.dto.ApplicationRequestDtoMapper;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/application-request/company")
@RestController
public class ApplicationRequestCompanyController {
    private final ApplicationRequestMemberService applicationRequestMemberService;
    private final ApplicationRequestDtoMapper applicationRequestDtoMapper;

    // 해당 역할에 지원한 출연자들(지원현황 화면)
    @GetMapping("/{roleId}/members")
    public ResponseEntity<?> readAllApplicationRequestMemberByStatus(
        @PathVariable(name = "roleId") Long roleId,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.ASC) Pageable pageable
    ){
        List<ApplicationRequestCompanyReadServiceResponseDto> applicationRequestCompanyReadServiceResponseDtoList =
            applicationRequestMemberService.getAppliedMembersByRole(
                roleId,
                pageable
            );
        return ResponseEntity.status(HttpStatus.OK)
            .body(applicationRequestCompanyReadServiceResponseDtoList);
    }
    // 요청 승인 및 거절(지원현황 화면)
    @PutMapping("{applicationRequestId}")
    public ResponseEntity<?> updateApplicationRequestMemberStatusToRejected(
        @PathVariable(name = "applicationRequestId") Long applicationRequestId,
        @Valid @RequestPart(name = "applicationRequestMemberUpdateControllerRequestDto")
        ApplicationRequestMemberUpdateControllerRequestDto applicationRequestMemberUpdateControllerRequestDto
    ) {
        ApplicationRequestMemberUpdateServiceRequestDto applicationRequestMemberUpdateServiceRequestDto =
            applicationRequestDtoMapper.toApplicationRequestMemberUpdateServiceRequestDto(
                applicationRequestMemberUpdateControllerRequestDto
            );
        applicationRequestMemberService.updateStatus(
            applicationRequestId,
            applicationRequestMemberUpdateServiceRequestDto
        );
        if (applicationRequestMemberUpdateServiceRequestDto.applyStatus() == ApplyStatus.APPROVED) {
            applicationRequestMemberService.createAttendanceManagementIfApproved(
                applicationRequestId,
                applicationRequestMemberUpdateServiceRequestDto
            );
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}