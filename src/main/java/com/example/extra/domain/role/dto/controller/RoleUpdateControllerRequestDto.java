package com.example.extra.domain.role.dto.controller;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record RoleUpdateControllerRequestDto(
    String roleName,
    String costume,
    Boolean sex,
    LocalDate minAge,
    LocalDate maxAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    Boolean checkTattoo
)
{

}
