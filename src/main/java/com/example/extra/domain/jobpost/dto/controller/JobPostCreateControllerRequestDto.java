package com.example.extra.domain.jobpost.dto.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record JobPostCreateControllerRequestDto(
    @Schema(description = "공고글 제목", example = "하이라키")
    @NotNull(message = "제목은 필수 입력 정보입니다")
    String title,
    @Schema(description = "집합 위치", example = "서울특별시 강남구 강남역 2번 출구")
    @NotNull(message = "집합 위치는 필수 입력 정보입니다")
    String gatheringLocation,
    @Schema(description = "집합 시간, 시간과 더불어 메모가 가능합니다", example = "5시까지 집합. 늦으면 혼쭐남")
    @NotNull(message = "집합 시간은 필수 입력 정보입니다")
    String gatheringTime,
    @Schema(description = "시급, 보편적으로 최저 시급이 들어갈 것 같습니다", example = "9860")
    @NotNull(message = "시급은 필수 입력 정보입니다")
    Integer hourPay,
    @Schema(description = "카테고리", example = "DRAMA")
    @NotNull(message = "카테고리는 필수 입력 정보입니다")
    @Pattern(
        regexp = "\\b(?:DRAMA|MOVIE|ADVERTISEMENT|MUSIC_VIDEO|ETC)\\b",
        message = "DRAMA, MOVIE, ADVERTISEMENT, MUSIC_VIDEO, ETC 중 하나를 입력해주세요"
    )
    String category
) {

}
