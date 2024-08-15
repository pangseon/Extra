package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.global.enums.Category;
import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record JobPostServiceResponseDto(
    Long id,
    String title,
    String gatheringLocation,
    String gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category,
    String companyName,
    List<Long> scheduleIdList,
    List<LocalDate> calenderList,
    List<Long> roleIdList,
    List<String> roleNameList,
    List<String> costumeList,
    List<Boolean> sexList,
    List<LocalDate> roleAgeList,
    List<Integer> limitPersonnelList,
    List<Integer> currentPersonnelList,
    List<Season> seasonList,
    List<Boolean> checkTattooList
) {

}
