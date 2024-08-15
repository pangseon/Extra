package com.example.extra.domain.applicationrequest.mapper.entity;

import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestCompanyReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.dto.service.ApplicationRequestMemberReadServiceResponseDto;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.enums.Category;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Slice;

@Mapper(componentModel = ComponentModel.SPRING, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ApplicationRequestEntityMapper {
    default ApplicationRequestMemberReadServiceResponseDto toApplicationRequestMemberReadServiceResponseDto(ApplicationRequestMember applicationRequestMember){
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

        // 업체의 정보
        String name = jobPost.getCompany().getName();

        return new ApplicationRequestMemberReadServiceResponseDto(
            id,
            category,
            title,
            gatheringTime,
            gatheringLocation,
            name,
            applyStatus
        );
    }

    List<ApplicationRequestMemberReadServiceResponseDto> toApplicationRequestMemberReadServiceResponseDtoList(Slice<ApplicationRequestMember> applicationRequestMemberSlice);

    default ApplicationRequestCompanyReadServiceResponseDto toApplicationRequestCompanyReadServiceResponseDto(ApplicationRequestMember applicationRequestMember){
        if ( applicationRequestMember == null ) {
            return null;
        }
        // 지원 요청 테이블 정보
        Long id = applicationRequestMember.getId();
        ApplyStatus applyStatus = applicationRequestMember.getApplyStatus();

        // 출연자 테이블 정보
        Long memberId = applicationRequestMember.getMember().getId();
        String name = applicationRequestMember.getMember().getName();

        return new ApplicationRequestCompanyReadServiceResponseDto(
            id,
            applyStatus,
            memberId,
            name
        );
    }
    List<ApplicationRequestCompanyReadServiceResponseDto> toApplicationRequestCompanyReadServiceResponseDtoList(Slice<ApplicationRequestMember> applicationRequestMemberSlice);
}
