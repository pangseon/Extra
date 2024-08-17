package com.example.extra.domain.role.dto.controller;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record RoleCreateControllerRequestDto(
    String roleName,
    String costume,
    Boolean sex,
    LocalDate minAge,
    LocalDate maxAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    TattooCreateControllerRequestDto tattoo
) {

}
