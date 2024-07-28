package com.example.extra.domain.member.dto.service.response;

import java.time.LocalDate;
import lombok.Builder;

@Builder
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
    Boolean face,
    Boolean chest,
    Boolean arm,
    Boolean leg,
    Boolean shoulder,
    Boolean back,
    Boolean hand,
    Boolean feet,
    String etc
) {

}
