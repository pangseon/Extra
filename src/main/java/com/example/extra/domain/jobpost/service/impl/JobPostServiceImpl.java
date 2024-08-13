package com.example.extra.domain.jobpost.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostServiceResponseDto;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.mapper.service.JobPostEntityMapper;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.NotFoundScheduleException;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
import com.example.extra.domain.schedule.repository.ScheduleRepository;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostEntityMapper jobPostEntityMapper;
    private final ScheduleRepository scheduleRepository;
    private final RoleRepository roleRepository;

    public void createJobPost(
        Company company,
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    ) {
        JobPost jobPost = jobPostEntityMapper.toJobPost(jobPostCreateServiceRequestDto, company);
        jobPostRepository.save(jobPost);
    }

    @Transactional
    public void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto
        , Company company
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id, company)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPost.updateJobPost(
            jobPostUpdateServiceRequestDto.title(),
            jobPostUpdateServiceRequestDto.gatheringLocation(),
            jobPostUpdateServiceRequestDto.gatheringTime(),
            jobPostUpdateServiceRequestDto.status(),
            jobPostUpdateServiceRequestDto.hourPay(),
            jobPostUpdateServiceRequestDto.category());
        jobPostRepository.save(jobPost);
    }

    public void deleteJobPost(
        Long jobPost_id
        , Company company
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id, company)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPostRepository.delete(jobPost);
    }

    @Transactional(readOnly = true)
    public JobPostServiceResponseDto readOnceJobPost(Long jobPost_id) {
        return readDto(jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST)));
    }

    @Transactional(readOnly = true)
    public List<JobPostServiceResponseDto> readAllJobPosts(int page) {
        Pageable pageable = PageRequest.of(page,5);
            return scheduleRepository.findAll(pageable)
                .stream()
                .sorted(Comparator.comparing(Schedule::getCalender).reversed()) // LocalDate를 기준으로 최신 날짜 순으로 정렬
                .map(Schedule::getJobPost)
                .distinct()
            .map(this::readDto)
            .toList();
    }

    private JobPostServiceResponseDto readDto(JobPost jobPost) {
        return JobPostServiceResponseDto.builder()
            .id(jobPost.getId())
            .title(jobPost.getTitle())
            .gatheringLocation(jobPost.getGatheringLocation())
            .gatheringTime(jobPost.getGatheringTime())
            .status(jobPost.getStatus())
            .hourPay(jobPost.getHourPay())
            .category(jobPost.getCategory())
            .company_name(jobPost.getCompany().getName())
            .calenderList(scheduleList(jobPost.getId())
                .stream()
                .map(Schedule::getCalender)
                .toList())
            .role_name_list(roleList(jobPost.getId())
                .stream()
                .map(Role::getRoleName)
                .toList())
            .costumeList(roleList(jobPost.getId())
                .stream()
                .map(Role::getCostume)
                .toList())
            .role_id_list(roleList(jobPost.getId())
                .stream()
                .map(Role::getId)
                .toList())
            .schedule_id_List(scheduleList(jobPost.getId())
                .stream()
                .map(Schedule::getId)
                .toList())
            .sexList(roleList(jobPost.getId())
                .stream()
                .map(Role::getSex)
                .toList())
            .check_tattoo_list(roleList(jobPost.getId())
                .stream()
                .map(Role::getCheckTattoo)
                .toList())
            .current_personnel_list(roleList(jobPost.getId())
                .stream()
                .map(Role::getCurrentPersonnel)
                .toList())
            .limit_personnel_list(roleList(jobPost.getId())
                .stream()
                .map(Role::getLimitPersonnel)
                .toList())
            .seasonList(roleList(jobPost.getId())
                .stream()
                .map(Role::getSeason)
                .toList())
            .build();
    }

    private List<Role> roleList(Long jobPost_id) {
        return roleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
    }

    private List<Schedule> scheduleList(Long jobPost_id) {
        return scheduleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
    }
}
