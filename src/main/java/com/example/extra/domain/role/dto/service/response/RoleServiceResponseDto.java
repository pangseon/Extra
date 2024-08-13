package com.example.extra.domain.role.dto.service.response;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record RoleServiceResponseDto(
    Long id,
    String roleName,
    String costume,
    Boolean sex,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    Boolean checkTattoo
)
{

}
