package com.example.extra.domain.jobpost.dto.service.request;

public record JobPostUpdateServiceRequestDto(
    String title,
    String gatheringLocation,
    String gatheringTime,
    String imageUrl,
    Boolean status,
    String category
) {

}
