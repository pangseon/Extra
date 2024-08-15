package com.example.extra.domain.member.dto.controller;

import java.time.LocalDate;

public record MemberUpdateControllerRequestDto(
    String name,
    boolean sex,
    LocalDate birthday,
    String home,
    float height,
    float weight,
    String phone,
    String introduction,
    String license,
    String pros,
    String bank,
    String accountNumber,
    boolean isImageChange
) {

}
