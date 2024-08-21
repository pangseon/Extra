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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Job", description = "공고글 정보 API")
@RestController
@RequestMapping("/api/v1/jobposts")
@RequiredArgsConstructor
public class JobPostController {

    // service
    private final JobPostService jobPostService;

    // mapper
    private final JobPostDtoMapper jobPostDtoMapper;

    @Operation(
        summary = "[업체] 공고글 생성",
        description = "공고글을 생성합니다."
    )
    @ApiResponse(responseCode = "201", description = "공고글 생성 성공", content = @Content(schema = @Schema(implementation = JobPostCreateServiceResponseDto.class)))
    @PostMapping("")
    public ResponseEntity<JobPostCreateServiceResponseDto> createJobPost(
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

    @Operation(
        summary = "[업체] 공고글 수정",
        description = "공고글 id와 입력된 정보를 통해 공고글을 수정합니다."
    )
    @PutMapping("/{jobpost_id}")
    public ResponseEntity<Void> updateJobPost(
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

    @Operation(
        summary = "[업체] 공고글 삭제",
        description = "공고글 id를 통해 공고글을 삭제합니다."
    )
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

    @Operation(
        summary = "공고글 단건 조회",
        description = "공고글 id를 통해 단건 조회를 진행합니다."
    )
    @ApiResponse(
        responseCode = "200", description = "공고글 단건 조회 성공",
        content = @Content(schema = @Schema(implementation = JobPostReadServiceResponseDto.class))
    )
    @GetMapping("/{jobpost_id}")
    public ResponseEntity<JobPostReadServiceResponseDto> readOnceJobPost(
        @PathVariable(name = "jobpost_id") Long jobPostId
    ) {
        return ResponseEntity
            .status(OK)
            .body(jobPostService.readOnceJobPost(jobPostId));
    }

    @Operation(
        summary = "공고글 전체 조회",
        description = "공고글 전체 조회를 진행합니다."
    )
    @ApiResponse(
        responseCode = "200", description = "공고글 전체 조회 성공",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobPostReadServiceResponseDto.class)))
    )
    @GetMapping("")
    public ResponseEntity<List<JobPostReadServiceResponseDto>> readAllJobPosts(
        @RequestParam int page
    ) {
        return ResponseEntity
            .status(OK)
            .body(jobPostService.readAllJobPosts(page));
    }

    @Operation(
        summary = "일정에 맞는 공고글 전체 조회",
        description = "일정에 맞는 공고글 전체 조회를 진행합니다."
    )
    @ApiResponse(
        responseCode = "200", description = "공고글 전체 조회 성공",
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = JobPostReadServiceResponseDto.class)))
    )
    @GetMapping("/calender")
    public ResponseEntity<List<JobPostReadServiceResponseDto>> readAllByCalenderJobPosts(
        @RequestParam String year,
        @RequestParam String month,
        @RequestParam int page
    ) {
        int calender_year = Integer.parseInt(year);
        int calender_month = Integer.parseInt(month);
        return ResponseEntity
            .status(OK)
            .body(jobPostService.readAllByCalenderJobPosts(
                page,
                calender_year,
                calender_month
            ));
    }

    @Operation(
        summary = "일정에 맞는 공고글 개수 조회",
        description = "일정에 맞는 공고글 개수 조회를 진행합니다."
    )
    @GetMapping("/calenders")
    public ResponseEntity<Map<LocalDate, List<Long>>> readJobPostIdsByMonth(
        @RequestParam String year,
        @RequestParam String month
    ) {
        int calender_year = Integer.parseInt(year);
        int calender_month = Integer.parseInt(month);
        return ResponseEntity
            .status(OK)
            .body(jobPostService.readJobPostIdsByMonth(
                calender_year,
                calender_month
            ));
    }
    @GetMapping("/companies/company")
    public List<JobPostReadServiceResponseDto> readJobPostsByCompany(
        @RequestParam int page,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return jobPostService.readACompanyJobPosts(page,userDetails.getAccount());
    }
    @GetMapping("/company/calenders")
    public ResponseEntity<Map<LocalDate, List<Long>>> readJobPostIdsByMonth(
        @RequestParam String year,
        @RequestParam String month,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        int calender_year = Integer.parseInt(year);
        int calender_month = Integer.parseInt(month);
        return ResponseEntity
            .status(OK)
            .body(jobPostService.readJobPostIdsByMonthAndAccount(
                calender_year,
                calender_month,
                userDetails.getAccount()
            ));
    }
}
