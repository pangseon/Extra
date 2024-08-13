package com.example.extra.domain.role.dto.service.request;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record RoleUpdateServiceRequestDto(
    String roleName,
    String costume,
    Boolean sex,
    LocalDate roleAge,
    LocalDate minAge,
    LocalDate maxAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    Boolean checkTattoo
)
{

}
