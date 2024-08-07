package com.example.extra.domain.jobpost.controller;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.controller.JobPostUpdateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostServiceResponseDto;
import com.example.extra.domain.jobpost.mapper.dto.JobPostDtoMapper;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.domain.role.mapper.dto.RoleDtoMapper;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/jobposts")
@RestController
public class JobPostController {

    private final JobPostDtoMapper jobPostDtoMapper;
    private final JobPostService jobPostService;
    private final RoleDtoMapper roleDtoMapper;

    @PostMapping("")
    public ResponseEntity<?> createJobPost(
        @Valid @RequestBody JobPostCreateControllerRequestDto jobPostCreateControllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto =
            jobPostDtoMapper.toJobPostCreateServiceDto(jobPostCreateControllerRequestDto);
        jobPostService.createJobPost(userDetails.getCompany(), jobPostCreateServiceRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{jobpost_id}")
    public ResponseEntity<?> updateJobPost(
        @PathVariable(name = "jobpost_id") Long jobpostId,
        @Valid @RequestBody JobPostUpdateControllerRequestDto jobPostUpdateControllerRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto =
            jobPostDtoMapper.toJobPostUpdateServiceDto(jobPostUpdateControllerRequestDto);
        jobPostService.updateJobPost(
            jobpostId
            , jobPostUpdateServiceRequestDto,
            userDetails.getCompany());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{jobpost_id}")
    public ResponseEntity<?> deleteJobPost(
        @PathVariable(name = "jobpost_id") Long jobpostId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        jobPostService.deleteJobPost(jobpostId, userDetails.getCompany());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{jobpost_id}")
    public JobPostServiceResponseDto readOnceJobPost(
        @PathVariable(name = "jobpost_id") Long jobpostId
    ) {
        return jobPostService.readOnceJobPost(jobpostId);
    }

    @GetMapping("")
    public List<JobPostServiceResponseDto> readAllJobPosts() {
        return jobPostService.readAllJobPosts();
    }

}
