package com.example.extra.domain.jobpost.dto.service.response;

import com.example.extra.global.enums.Category;
import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record JobPostServiceResponseDto(
    Long id,
    String title,
    String gatheringLocation,
    LocalDateTime gatheringTime,
    String imageUrl,
    Boolean status,
    Integer hourPay,
    Category category,
    String company_name,
    List<Long> schedule_id_List,
    List<LocalDate> calenderList,
    List<Long> role_id_list,
    List<String> role_name_list,
    List<String> costumeList,
    List<Boolean> sexList,
    List<LocalDate> role_age_list,
    List<Integer> limit_personnel_list,
    List<Integer> current_personnel_list,
    List<Season> seasonList,
    List<Boolean> check_tattoo_list
) {
    public LocalDate latestCalenderDate() {
        return calenderList.stream()
            .max(LocalDate::compareTo)
            .orElse(LocalDate.MIN);
    }
}
