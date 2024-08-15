package com.example.extra.domain.role.dto.service.response;

import com.example.extra.global.enums.Season;

public record RoleServiceResponseDto(
    Long id,
    String roleName,
    String costume,
    Boolean sex,
    Integer min_Age,
    Integer max_Age,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    Boolean checkTattoo
) {

}
