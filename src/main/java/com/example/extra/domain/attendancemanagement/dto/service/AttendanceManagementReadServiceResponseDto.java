package com.example.extra.domain.attendancemanagement.dto.service;

import lombok.Builder;

// 관리자-출연자 목록 화면
@Builder
public record AttendanceManagementReadServiceResponseDto(
    Long Id,
    Long memberId,
    String memberName,
    String roleName,
    Boolean isAttended // AttendanceMangement의 출근 유무
)
{

}


