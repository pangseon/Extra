package com.example.extra.domain.member.dto.service.response;

import com.example.extra.domain.tattoo.entity.Tattoo;
import java.time.LocalDate;

public record MemberReadServiceResponseDto(
    String name,
    Boolean sex,
    LocalDate birthday,
    String home,
    Float height,
    Float weight,
    String introduction,
    String license,
    String pros,
    Tattoo tattoo
) {

}
