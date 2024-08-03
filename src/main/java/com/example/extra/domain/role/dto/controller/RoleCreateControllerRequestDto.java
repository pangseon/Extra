package com.example.extra.domain.role.dto.controller;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RoleCreateControllerRequestDto(
    String role_name,
    String costume,
    Boolean sex,
    LocalDate role_age,
    Integer limit_personnel,
    Integer current_personnel,
    Season season
)
{

}
