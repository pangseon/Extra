package com.example.extra.domain.role.dto.service;

import com.example.extra.global.enums.Season;
import java.time.LocalDateTime;

public record RoleCreateServiceRequestDto(
    String role_name,
    String costume,
    Boolean sex,
    LocalDateTime role_age,
    Integer limit_personnel,
    Integer current_personnel,
    Season season
)
{

}
