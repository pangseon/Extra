package com.example.extra.domain.jobpost.dto.service.request;

public record JobPostCreateServiceRequestDto(
    String title,
    String gatheringLocation,
    String gatheringTime,
    Integer hourPay,
    String category
) {

}
