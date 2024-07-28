package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;

// TODO - response dto 2개로 쪼개기(지원현황 화면과 출연자 목록 화면에 표시될 정보 다르다)
public record ApplicationRequestCompanyReadServiceResponseDto(
    Long id, // ApplicationRequestMember의 id(지원 현황, 출연자 목록)
    ApplyStatus applyStatus, // ApplicationRequestMember의 지원상태(지원 현황?)
    Long memberId, // Member의 id(지원 현황, 출연자 목록)
    String name // Member의 이름(지원 현황, 출연자 목록)
//    String roleName, // Role의 이름(출연자 목록)
//    Boolean isAttended // AttendanceManagement의 출근 유무(출연자 목록)
//    // TODO - Member의 온도, 경력 (정렬 필요)
)
{

}
