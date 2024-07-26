package com.example.extra.domain.member.dto.service.request;

import java.time.LocalDate;

public record MemberCreateServiceRequestDto(
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
    String account_number

    /**
     * 계좌 관련 정보(임시)
     * bank : 은행명
     * account_number : 계좌번호
     */

    /**
     * 약관 동의 내용 필요
     */

) {

}
