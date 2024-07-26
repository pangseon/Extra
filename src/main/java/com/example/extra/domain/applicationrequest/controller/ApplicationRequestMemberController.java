package com.example.extra.domain.applicationrequest.controller;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.service.ApplicationRequestMemberService;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/application-request/member")
@RestController
public class ApplicationRequestMemberController {
    private final ApplicationRequestMemberService applicationRequestMemberService;

    @PostMapping("/role/{roleId}")
    public ResponseEntity<?> createApplicationRequestMember(
        @PathVariable(name = "roleId") Long roleId
    ){
        applicationRequestMemberService.createApplicationRequestMember(roleId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/roles")
    public ResponseEntity<?> readAllApplicationRequestMember(
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ){
        List<ApplicationRequestMemberReadServiceResponseDto> appliedRoleList =
            applicationRequestMemberService.getAppliedRoles(pageable);

        return ResponseEntity.status(HttpStatus.OK)
            .body(appliedRoleList);
    }

    @GetMapping("/roles/{status}")
    public ResponseEntity<?> readAllApplicationRequestMemberByStatus(
        @PathVariable(name = "status") String applyStatusString,
        @PageableDefault(size = 10, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ){
        ApplyStatus applyStatus = ApplyStatus.fromString(applyStatusString);
        if (applyStatus == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<ApplicationRequestMemberReadServiceResponseDto> appliedRoleList =
            applicationRequestMemberService.getAppliedRolesByStatus(
                applyStatus,
                pageable
        );
        return ResponseEntity.status(HttpStatus.OK)
            .body(appliedRoleList);
    }

    @DeleteMapping("role/{roleId}")
    public ResponseEntity<?> deleteApplicationRequestMember(
        @PathVariable(name = "roleId") Long roleId
    ){
        applicationRequestMemberService.deleteApplicationRequestMember(roleId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}