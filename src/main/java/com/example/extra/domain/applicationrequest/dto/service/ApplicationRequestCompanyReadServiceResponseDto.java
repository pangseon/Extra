package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;

// 지원 현황 화면
public record ApplicationRequestCompanyReadServiceResponseDto(
    Long id, // ApplicationRequestMember의 id
    ApplyStatus applyStatus, // ApplicationRequestMember의 지원상태
    Long memberId,
    String name
    // TODO - Member의 온도, 경력 (정렬 필요)
)
{

}
