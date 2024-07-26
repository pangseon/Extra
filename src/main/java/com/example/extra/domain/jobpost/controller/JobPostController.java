package com.example.extra.domain.jobpost.controller;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.mapper.dto.JobPostDtoMapper;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.domain.role.dto.controller.RoleCreateControllerRequestDto;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.mapper.dto.RoleDtoMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobposts")
@RestController
public class JobPostController {
    private final JobPostDtoMapper jobPostDtoMapper;
    private final JobPostService jobPostService;
    private final RoleDtoMapper roleDtoMapper;

    @PostMapping("/create")
    public ResponseEntity<?> createJobPost(
        @Valid @RequestPart(name = "jobPostCreateRequestDto") JobPostCreateControllerRequestDto jobPostCreateControllerRequestDto,
        @Valid @RequestPart(name = "roleCreateRequestDto")List<RoleCreateControllerRequestDto> roleCreateControllerRequestDtoList
    ){
        JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto =
            jobPostDtoMapper.toJobPostCreateServiceDto(jobPostCreateControllerRequestDto);
        List<RoleCreateServiceRequestDto> roleCreateServiceRequestDtoList =
            roleDtoMapper.toRoleCreateServiceRequestDtoList(roleCreateControllerRequestDtoList);
        jobPostService.createJobPost(jobPostCreateServiceRequestDto,roleCreateServiceRequestDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}