package com.example.extra.domain.jobpost.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostCreateServiceResponseDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostReadServiceResponseDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface JobPostService {

    JobPostCreateServiceResponseDto createJobPost(
        Account account,
        JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    );

    void updateJobPost(
        Account account,
        Long jobPost_id,
        JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto
    );

    void deleteJobPost(
        Account account,
        Long jobPost_id
    );

    JobPostReadServiceResponseDto readOnceJobPost(Long jobPost_id);

    List<JobPostReadServiceResponseDto> readAllJobPosts(int page);

    List<JobPostReadServiceResponseDto> readAllByCalenderJobPosts(
        int page,
        int year,
        int month
    );
    Map<LocalDate, List<Long>> readJobPostIdsByMonth(int year, int month);
    List<JobPostReadServiceResponseDto> readACompanyJobPosts(int page,Account account);
}
