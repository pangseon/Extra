package com.example.extra.domain.jobpost.service;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostServiceResponseDto;
import java.util.List;

public interface JobPostService {

    void createJobPost(
        Account account,
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    );

    void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto,
        Account account
    );

    void deleteJobPost(
        Long jobPost_id,
        Account account
    );

    JobPostServiceResponseDto readOnceJobPost(
        Long jobPost_id);

    List<JobPostServiceResponseDto> readAllJobPosts();
}
