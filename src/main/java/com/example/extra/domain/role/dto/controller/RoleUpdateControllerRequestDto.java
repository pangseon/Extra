package com.example.extra.domain.role.dto.controller;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record RoleUpdateControllerRequestDto(
    String role_name,
    String costume,
    Boolean sex,
    LocalDate role_age,
    Integer limit_personnel,
    Integer current_personnel,
    Season season,
    Boolean check_tattoo
)
{

}
