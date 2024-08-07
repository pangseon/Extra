package com.example.extra.domain.role.dto.controller;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record RoleCreateControllerRequestDto(
    String roleName,
    String costume,
    Boolean sex,
    LocalDate roleAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season,
    Boolean checkTattoo

)
{

}
