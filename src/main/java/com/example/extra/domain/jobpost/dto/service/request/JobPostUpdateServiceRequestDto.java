package com.example.extra.domain.jobpost.dto.service.request;

import com.example.extra.global.enums.Category;
import java.time.LocalDateTime;

public record JobPostUpdateServiceRequestDto(
    String title,
    String gatheringLocation,
    String gatheringTime,
    String imageUrl,
    Boolean status,
    Category category
) {

}
