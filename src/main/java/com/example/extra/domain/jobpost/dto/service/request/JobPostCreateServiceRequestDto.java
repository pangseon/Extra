package com.example.extra.domain.jobpost.dto.service.request;

import com.example.extra.global.enums.Category;

public record JobPostCreateServiceRequestDto(
    String title,
    String gatheringLocation,
    String gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category
) {

}
