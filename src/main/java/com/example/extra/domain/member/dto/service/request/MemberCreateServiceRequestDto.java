package com.example.extra.domain.member.dto.service.request;

import java.time.LocalDate;

public record MemberCreateServiceRequestDto(
    String phone,
    String name,
    LocalDate birthday,
    Boolean sex,
    String home,
    Float height,
    Float weight,
    String bank,
    String accountNumber
    /**
     * 약관 동의 내용 필요
     */
) {

}
