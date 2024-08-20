package com.example.extra.domain.jobpost.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.example.extra.domain.jobpost.dto.controller.JobPostCreateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.controller.JobPostUpdateControllerRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostCreateServiceResponseDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostReadServiceResponseDto;
import com.example.extra.domain.jobpost.mapper.dto.JobPostDtoMapper;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/jobposts")
@RequiredArgsConstructor
public class JobPostController {

    // service
    private final JobPostService jobPostService;

    // mapper
    private final JobPostDtoMapper jobPostDtoMapper;

    @PostMapping("")
    public ResponseEntity<?> createJobPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody JobPostCreateControllerRequestDto controllerRequestDto
    ) {
        JobPostCreateServiceRequestDto serviceRequestDto =
            jobPostDtoMapper.toJobPostCreateServiceDto(controllerRequestDto);

        JobPostCreateServiceResponseDto serviceResponseDto = jobPostService.createJobPost(
            userDetails.getAccount(),
            serviceRequestDto
        );

        return ResponseEntity
            .status(CREATED)
            .body(serviceResponseDto);
    }

    @PutMapping("/{jobpost_id}")
    public ResponseEntity<?> updateJobPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobpost_id") Long jobPostId,
        @Valid @RequestBody JobPostUpdateControllerRequestDto controllerRequestDto
    ) {
        JobPostUpdateServiceRequestDto serviceRequestDto =
            jobPostDtoMapper.toJobPostUpdateServiceDto(controllerRequestDto);

        jobPostService.updateJobPost(
            userDetails.getAccount(),
            jobPostId,
            serviceRequestDto
        );

        return ResponseEntity
            .status(CREATED)
            .build();
    }

    @DeleteMapping("/{jobpost_id}")
    public ResponseEntity<?> deleteJobPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "jobpost_id") Long jobPostId
    ) {
        jobPostService.deleteJobPost(
            userDetails.getAccount(),
            jobPostId
        );

        return ResponseEntity
            .status(OK)
            .build();
    }

    @GetMapping("/{jobpost_id}")
    public JobPostReadServiceResponseDto readOnceJobPost(
        @PathVariable(name = "jobpost_id") Long jobPostId
    ) {
        return jobPostService.readOnceJobPost(jobPostId);
    }

    @GetMapping("")
    public List<JobPostReadServiceResponseDto> readAllJobPosts(
        @RequestParam int page
    ) {
        return jobPostService.readAllJobPosts(page);
    }

    @GetMapping("/calender")
    public List<JobPostReadServiceResponseDto> readAllByCalenderJobPosts(
        @RequestParam String year,
        @RequestParam String month,
        @RequestParam int page
    ) {
        int calender_year = Integer.parseInt(year);
        int calender_month = Integer.parseInt(month);
        return jobPostService.readAllByCalenderJobPosts(page, calender_year, calender_month);
    }

    @GetMapping("/calenders")
    public Map<LocalDate, List<Long>> readJobPostIdsByMonth(
        @RequestParam String year,
        @RequestParam String month
    ) {
        int calender_year = Integer.parseInt(year);
        int calender_month = Integer.parseInt(month);
        return jobPostService.readJobPostIdsByMonth(calender_year, calender_month);
    }
}
