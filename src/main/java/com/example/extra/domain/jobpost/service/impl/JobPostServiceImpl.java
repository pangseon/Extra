package com.example.extra.domain.jobpost.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.dto.service.request.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.request.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostCreateServiceResponseDto;
import com.example.extra.domain.jobpost.dto.service.response.JobPostReadServiceResponseDto;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.mapper.service.JobPostEntityMapper;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.exception.RoleException;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
import com.example.extra.domain.schedule.exception.ScheduleException;
import com.example.extra.domain.schedule.repository.ScheduleRepository;
import com.example.extra.domain.tattoo.dto.service.response.TattooReadServiceResponseDto;
import com.example.extra.global.enums.Category;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    // repository
    private final JobPostRepository jobPostRepository;
    private final ScheduleRepository scheduleRepository;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    // mapper
    private final JobPostEntityMapper jobPostEntityMapper;

    @Override
    @Transactional
    public JobPostCreateServiceResponseDto createJobPost(
        final Account account,
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    ) {
        JobPost jobPost = jobPostEntityMapper.toJobPost(
            jobPostCreateServiceRequestDto,
            Category.fromString(jobPostCreateServiceRequestDto.category()),
            getCompanyByAccount(account)
        );

        jobPost = jobPostRepository.save(jobPost);

        return new JobPostCreateServiceResponseDto(jobPost.getId());
    }

    @Override
    @Transactional
    public void updateJobPost(
        final Account account,
        final Long jobPostId,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto
    ) {
        JobPost jobPost = getJobPostByAccountAndId(account, jobPostId);

        jobPost.updateJobPost(
            jobPostUpdateServiceRequestDto.title(),
            jobPostUpdateServiceRequestDto.gatheringLocation(),
            jobPostUpdateServiceRequestDto.gatheringTime(),
            jobPostUpdateServiceRequestDto.status(),
            Category.fromString(jobPostUpdateServiceRequestDto.category())
        );

        jobPostRepository.save(jobPost);
    }

    @Override
    @Transactional
    public void deleteJobPost(
        final Account account,
        final Long jobPostId
    ) {
        JobPost jobPost = getJobPostByAccountAndId(account, jobPostId);

        jobPostRepository.delete(jobPost);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostReadServiceResponseDto readOnceJobPost(final Long jobPostId) {
        return readDto(getJobPostById(jobPostId));
    }


    @Override
    @Transactional(readOnly = true)
    public List<JobPostReadServiceResponseDto> readAllJobPosts(final int page) {
        return scheduleRepository
            .findAll(getDefaultPageable(page))
            .stream()
            .sorted(Comparator
                .comparing(Schedule::getCalender)
                .reversed()) // LocalDate를 기준으로 최신 날짜 순으로 정렬
            .map(Schedule::getJobPost)
            .distinct()
            .map(this::readDto)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobPostReadServiceResponseDto> readAllByCalenderJobPosts(
        final int page,
        final int year,
        final int month
    ) {
        // 특정 연도와 월에 해당하는 Schedule들을 필터링
        List<Schedule> filteredScheduleList = scheduleRepository
            .findAll(getDefaultPageable(page))
            .stream()
            .filter(schedule -> {
                LocalDate date = schedule.getCalender();
                return date.getYear() == year && date.getMonthValue() == month;
            })
            .toList();

        // 필터링된 Schedule 리스트에서 JobPost 객체들을 추출하여 중복 제거 후 DTO로 변환
        return filteredScheduleList.stream()
            .map(Schedule::getJobPost)  // Schedule에서 JobPost를 추출
            .distinct()                 // 중복 제거
            .map(this::readDto)         // DTO로 변환
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, List<Long>> readJobPostIdsByMonth(
        final int year,
        final int month
    ) {
        // 해당 연도와 월에 해당하는 LocalDate 범위를 생성
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        // 특정 월에 해당하는 Schedule들을 필터링하고, 날짜별로 JobPost ID를 매핑
        return scheduleRepository
            .findAll() // 페이징이 필요 없으므로 findAll() 사용
            .stream()
            .filter(schedule -> {
                LocalDate date = schedule.getCalender();
                return !date.isBefore(startOfMonth)
                    && !date.isAfter(endOfMonth); // 해당 월에 속하는 Schedule만 필터링
            })
            .collect(Collectors.groupingBy(
                Schedule::getCalender, // LocalDate를 기준으로 그룹화
                Collectors.mapping(schedule -> schedule.getJobPost().getId(), Collectors.toList())
                // JobPost ID를 리스트로 매핑
            ));
    }

    private JobPostReadServiceResponseDto readDto(final JobPost jobPost) {
        return JobPostReadServiceResponseDto.builder()
            .id(jobPost.getId())
            .title(jobPost.getTitle())
            .gatheringLocation(jobPost.getGatheringLocation())
            .gatheringTime(jobPost.getGatheringTime())
            .status(jobPost.getStatus())
            .hourPay(9860)      // 추후 수정, 현재 최저 시급
            .category(jobPost.getCategory())
            .companyName(jobPost.getCompany().getName())
            .calenderList(scheduleList(jobPost.getId())
                .stream()
                .map(Schedule::getCalender)
                .toList())
            .roleNameList(roleList(jobPost.getId())
                .stream()
                .map(Role::getRoleName)
                .toList())
            .costumeList(roleList(jobPost.getId())
                .stream()
                .map(Role::getCostume)
                .toList())
            .roleIdList(roleList(jobPost.getId())
                .stream()
                .map(Role::getId)
                .toList())
            .scheduleIdList(scheduleList(jobPost.getId())
                .stream()
                .map(Schedule::getId)
                .toList())
            .sexList(roleList(jobPost.getId())
                .stream()
                .map(Role::getSex)
                .toList())
            .roleAgeList(roleList(jobPost.getId())
                .stream()
                .map(Role::getAgeToString)
                .toList())
            .tattooList(roleList(jobPost.getId())
                .stream()
                .map(Role::getTattoo)
                .map(TattooReadServiceResponseDto::from)
                .toList())
            .currentPersonnelList(roleList(jobPost.getId())
                .stream()
                .map(Role::getCurrentPersonnel)
                .toList())
            .limitPersonnelList(roleList(jobPost.getId())
                .stream()
                .map(Role::getLimitPersonnel)
                .toList())
            .seasonList(roleList(jobPost.getId())
                .stream()
                .map(Role::getSeason)
                .toList())
            .build();
    }
    
    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    private JobPost getJobPostById(final Long jobPostId) {
        return jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
    }

    private JobPost getJobPostByAccountAndId(final Account account, final Long jobPostId) {
        return jobPostRepository.findByIdAndCompany(
            jobPostId,
            getCompanyByAccount(account)
        ).orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
    }

    private PageRequest getDefaultPageable(final int page) {
        return PageRequest.of(page, 5);
    }

    private List<Role> roleList(Long jobPost_id) {
        return roleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
    }

    private List<Schedule> scheduleList(Long jobPost_id) {
        return scheduleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
    }
}
