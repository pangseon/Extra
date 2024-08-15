package com.example.extra.domain.jobpost.dto.controller;

import com.example.extra.global.enums.Category;

public record JobPostCreateControllerRequestDto(
    String title,
    String gatheringLocation,
    String gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category
) {

}
