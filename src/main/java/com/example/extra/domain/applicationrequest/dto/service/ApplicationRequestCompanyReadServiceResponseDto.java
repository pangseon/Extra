package com.example.extra.domain.applicationrequest.dto.service;

import java.time.LocalDate;

public record ApplicationRequestCompanyReadServiceResponseDto(
    Long id,
    String name,
    Boolean sex,
    LocalDate birthDay,
    String home,
    Float height,
    Float weight,
    String phone,
    String introduction,
    String license,
    String pros
)
{

}
