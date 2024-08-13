package com.example.extra.domain.jobpost.service;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostServiceResponseDto;
import java.util.List;

public interface JobPostService {
    void createJobPost(
        Company company,
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    );
    void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto,
        Company company
    );
    void deleteJobPost(
        Long jobPost_id,
        Company company
    );
    JobPostServiceResponseDto readOnceJobPost(
        Long jobPost_id);
    List<JobPostServiceResponseDto> readAllJobPosts(int page);
}
