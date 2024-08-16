package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.domain.jobpost.entity.JobPost;
import lombok.Builder;

@Builder
public record JobPostCreateServiceResponseDto(
    Long id
) {
    public static JobPostCreateServiceResponseDto from(JobPost jobPost){
        return JobPostCreateServiceResponseDto.builder()
                .id(jobPost.getId())
            .build();
    }
}
