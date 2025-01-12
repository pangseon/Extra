package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.domain.tattoo.dto.service.response.TattooReadServiceResponseDto;
import com.example.extra.global.enums.Category;
import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record JobPostReadServiceResponseDto(
    Long id,
    String title,
    String gatheringLocation,
    String gatheringTime,
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
    List<String> roleAgeList,
    List<Integer> limitPersonnelList,
    List<Integer> currentPersonnelList,
    List<Season> seasonList,
    List<TattooReadServiceResponseDto> tattooList
) {

}
