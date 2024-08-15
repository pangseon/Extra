package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.global.enums.ApplyStatus;
import lombok.Builder;

// 지원 현황 화면
@Builder
public record ApplicationRequestCompanyReadServiceResponseDto(
    Long id, // ApplicationRequestMember의 id
    ApplyStatus applyStatus, // ApplicationRequestMember의 지원상태
    Long memberId,
    String name
    // TODO - Member의 온도, 경력 (정렬 필요)
)
{
    public static ApplicationRequestCompanyReadServiceResponseDto from(
        ApplicationRequestMember applicationRequestMember
    ) {
        if ( applicationRequestMember == null ) {
            return null;
        }
        // 지원 요청 테이블 정보
        Long id = applicationRequestMember.getId();
        ApplyStatus applyStatus = applicationRequestMember.getApplyStatus();

        // 출연자 테이블 정보
        Long memberId = applicationRequestMember.getMember().getId();
        String name = applicationRequestMember.getMember().getName();

        return ApplicationRequestCompanyReadServiceResponseDto
            .builder()
                .id(id)
                .applyStatus(applyStatus)
                .memberId(memberId)
                .name(name)
            .build();
    }
}
