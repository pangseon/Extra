package com.example.extra.domain.applicationrequest.controller;

import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestCreateControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.controller.ApplicationRequestDeleteControllerRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCreateServiceRequestDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestDeleteServiceRequestDto;
import com.example.extra.domain.applicationrequest.mapper.dto.ApplicationRequestDtoMapper;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.global.enums.ApplyStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/applicationRequest/member")
@RestController
public class ApplicationRequestMemberController {
    private final ApplicationRequestDtoMapper applicationRequestDtoMapper;
    private final ApplicationRequestMemberService applicationRequestMemberService;

    @PostMapping("/application")
    public ResponseEntity<?> createApplicationRequestMember(
        @Valid @RequestPart(name = "applicationRequestCreateControllerRequestDto")
        ApplicationRequestCreateControllerRequestDto applicationRequestCreateControllerRequestDto
    ){
        ApplicationRequestCreateServiceRequestDto applicationRequestCreateServiceRequestDto =
            applicationRequestDtoMapper.toApplicationRequestCreateServiceRequestDto(applicationRequestCreateControllerRequestDto);

        applicationRequestMemberService.createApplicationRequestMember(applicationRequestCreateServiceRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/application")
    public ResponseEntity<?> readAllApplicationRequestMember(
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ){
        // TODO - RoleReadResponseDto 정해지면 바꾸기
        Slice<RoleCreateControllerRequestDto> appliedRoleSlice =
            applicationRequestMemberService.getAppliedRoles(pageable);

        return ResponseEntity.status(HttpStatus.OK)
            .body(appliedRoleSlice);
    }

    @GetMapping("/application/{status}")
    public ResponseEntity<?> readAllApplicationRequestMemberByStatus(
        @PathVariable(name = "status") ApplyStatus applyStatus,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ){
        // TODO - RoleReadResponseDto 정해지면 바꾸기
        Slice<RoleCreateControllerRequestDto> appliedRoleSlice =
            applicationRequestMemberService.getAppliedRolesByStatus(
                applyStatus,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK)
            .body(appliedRoleSlice);
    }

    @PostMapping("application/delete")
    public ResponseEntity<?> deleteApplicationRequestMember(
        @Valid @RequestPart(name = "applicationRequestDeleteControllerRequestDto")
        ApplicationRequestDeleteControllerRequestDto applicationRequestDeleteControllerRequestDto
    ){
        ApplicationRequestDeleteServiceRequestDto applicationRequestDeleteServiceRequestDto =
            applicationRequestDtoMapper.toApplicationRequestDeleteServiceRequestDto(applicationRequestDeleteControllerRequestDto);

        applicationRequestMemberService.deleteApplicationRequestMember(applicationRequestDeleteServiceRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
