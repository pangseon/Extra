package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.domain.jobpost.entity.JobPost;
import lombok.Builder;

@Builder
public record JobPostCreateServiceResponseDto(
    Long id
) {

}
