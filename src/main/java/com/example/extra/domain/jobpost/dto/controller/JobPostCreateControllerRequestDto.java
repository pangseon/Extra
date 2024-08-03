package com.example.extra.domain.jobpost.dto.controller;

import com.example.extra.global.enums.Category;
import java.time.LocalDateTime;

public record JobPostCreateControllerRequestDto(
    String title,
    String gatheringLocation,
    LocalDateTime gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category
) {

}
