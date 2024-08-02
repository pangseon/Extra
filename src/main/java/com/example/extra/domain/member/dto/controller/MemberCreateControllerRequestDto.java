package com.example.extra.domain.member.dto.controller;

import java.time.LocalDate;

public record MemberCreateControllerRequestDto(
    String email,
    String password,
    String phone,
    String name,
    LocalDate birthday,
    Boolean sex,
    String home,
    Float height,
    Float weight,
    String bank,
    String accountNumber,
    Boolean isAdmin,
    String adminToken

    /**
     * 약관 동의 내용 필요
     */
) {

}
