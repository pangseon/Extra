package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.enums.Category;

public record ApplicationRequestMemberReadServiceResponseDto(
    Long id, // ApplicationRequestMember 테이블의 기본키
    Category category, // 공고글의 카테고리
    String title, // 공고글의 촬영 작품 제목
    String gatheringTime, // 공고글의 집합 시간
    String gatheringLocation, // 공고글의 집합 위치
    String name, // 하청업체의 이름
    ApplyStatus applyStatus // ApplicationRequestMember 테이블의 지원 상태
) {

}


