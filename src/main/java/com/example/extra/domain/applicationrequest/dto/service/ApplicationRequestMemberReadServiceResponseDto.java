package com.example.extra.domain.applicationrequest.dto.service;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.enums.Category;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;

@Builder
public record ApplicationRequestMemberReadServiceResponseDto(
    Long id, // ApplicationRequestMember 테이블의 기본키
    Long jobPostId, // 공고글의 id
    Category category, // 공고글의 카테고리
    String title, // 공고글의 촬영 작품 제목
    String gatheringTime, // 공고글의 집합 시간
    String gatheringLocation, // 공고글의 집합 위치
    List<LocalDate> calenderList, // 공고글의 일정 list
    String name, // 하청업체의 이름
    ApplyStatus applyStatus // ApplicationRequestMember 테이블의 지원 상태
)
{
    public static ApplicationRequestMemberReadServiceResponseDto from(
        ApplicationRequestMember applicationRequestMember
    ) {
        if ( applicationRequestMember == null ) {
            return null;
        }
        JobPost jobPost = applicationRequestMember.getRole().getJobPost();

        // 지원 요청 테이블 정보
        Long id = applicationRequestMember.getId();
        ApplyStatus applyStatus = applicationRequestMember.getApplyStatus();

        // 공고글의 정보
        Category category = jobPost.getCategory();
        String title = jobPost.getTitle();
        String  gatheringTime = jobPost.getGatheringTime();
        String gatheringLocation = jobPost.getGatheringLocation();
        List<LocalDate> calenderList = jobPost.getScheduleList()
            .stream()
            .map(Schedule::getCalender)
            .sorted()
            .toList();

        // 업체의 정보
        String name = jobPost.getCompany().getName();

        return ApplicationRequestMemberReadServiceResponseDto
            .builder()
                .id(applicationRequestMember.getId())
                .jobPostId(jobPost.getId())
                .category(category)
                .title(title)
                .gatheringTime(gatheringTime)
                .gatheringLocation(gatheringLocation)
                .calenderList(calenderList)
                .name(name)
                .applyStatus(applyStatus)
            .build();
    }
}


