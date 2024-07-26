package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.Season;
import java.time.LocalDate;

public record ApplicationRequestMemberReadServiceResponseDto(
    Long id,
    String roleName,
    String costume,
    Boolean sex,
    LocalDate roleAge,
    Integer limitPersonnel,
    Integer currentPersonnel,
    Season season
)
{

}
