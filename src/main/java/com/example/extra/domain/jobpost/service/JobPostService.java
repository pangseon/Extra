package com.example.extra.domain.jobpost.service;

import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostServiceResponseDto;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import java.util.List;

public interface JobPostService {
    void createJobPost(
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto,
        final List<RoleCreateServiceRequestDto> roleCreateServiceRequestDtoList
    );
    void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto
    );
    void deleteJobPost(
        Long jobPost_id
    );
    JobPostServiceResponseDto readOnceJobPost(
        Long jobPost_id);
    List<JobPostServiceResponseDto> readAllJobPosts();
}
