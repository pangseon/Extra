package com.example.extra.domain.member.dto.service.request;

import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import java.time.LocalDate;

public record MemberCreateServiceRequestDto(
    Long accountId,
    String phone,
    String name,
    LocalDate birthday,
    Boolean sex,
    String home,
    Float height,
    Float weight,
    String bank,
    String accountNumber,
    TattooCreateControllerRequestDto tattoo
    /**
     * 약관 동의 내용 필요
     */
) {

}
