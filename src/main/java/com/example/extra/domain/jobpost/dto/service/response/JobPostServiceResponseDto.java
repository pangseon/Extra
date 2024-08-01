package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.global.enums.Category;
import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;

public record JobPostServiceResponseDto(
    String dramaTitle,
    String gatheringLocation,
    LocalDateTime gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category,
    String company_name
/*    ,LocalDateTime localDateTime,
    String role_name,
    String costume,
    Boolean sex,
    LocalDate role_age,
    Integer limit_person,
    Integer current_personnel,
    Season season,
    Boolean check_tattoo*/
) {

}
