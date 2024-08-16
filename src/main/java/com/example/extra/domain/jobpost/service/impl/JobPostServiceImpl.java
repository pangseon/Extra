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
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.NotFoundScheduleException;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
import com.example.extra.domain.schedule.repository.ScheduleRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final JobPostEntityMapper jobPostEntityMapper;
    private final ScheduleRepository scheduleRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    private Member getMemberByAccount(final Account account) {
        return memberRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    public JobPostCreateServiceResponseDto createJobPost(
        final Account account,
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto
    ) {
        JobPost jobPost = jobPostEntityMapper.toJobPost(jobPostCreateServiceRequestDto,
            getCompanyByAccount(account));
        jobPost = jobPostRepository.save(jobPost);
        return JobPostCreateServiceResponseDto.from(jobPost);
    }

    @Transactional
    public void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto,
        final Account account
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPost.updateJobPost(
            jobPostUpdateServiceRequestDto.title(),
            jobPostUpdateServiceRequestDto.gatheringLocation(),
            jobPostUpdateServiceRequestDto.gatheringTime(),
            jobPostUpdateServiceRequestDto.status(),
            jobPostUpdateServiceRequestDto.category());
        jobPostRepository.save(jobPost);
    }

    public void deleteJobPost(
        Long jobPost_id,
        final Account account
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPostRepository.delete(jobPost);
    }

    @Transactional(readOnly = true)
    public JobPostReadServiceResponseDto readOnceJobPost(Long jobPost_id) {
        return readDto(jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST)));
    }

    @Transactional(readOnly = true)
    public List<JobPostReadServiceResponseDto> readAllJobPosts(int page) {
        Pageable pageable = PageRequest.of(page,5);
            return scheduleRepository.findAll(pageable)
                .stream()
                .sorted(Comparator.comparing(Schedule::getCalender).reversed()) // LocalDate를 기준으로 최신 날짜 순으로 정렬
                .map(Schedule::getJobPost)
                .distinct()
            .map(this::readDto)
            .toList();
    }
    @Transactional(readOnly = true)
    public List<JobPostReadServiceResponseDto> readAllByCalenderJobPosts(int page, int year, int month) {
        Pageable pageable = PageRequest.of(page, 5);
        // 특정 연도와 월에 해당하는 Schedule들을 필터링
        List<Schedule> filteredSchedules = scheduleRepository.findAll(pageable)
            .stream()
            .filter(schedule -> {
                LocalDate date = schedule.getCalender();
                return date.getYear() == year && date.getMonthValue() == month;
            })
            .toList();
        // 필터링된 Schedule 리스트에서 JobPost 객체들을 추출하여 중복 제거 후 DTO로 변환
        return filteredSchedules.stream()
            .map(Schedule::getJobPost) // Schedule에서 JobPost를 추출
            .distinct() // 중복 제거
            .map(this::readDto) // DTO로 변환
            .toList();
    }
    @Transactional(readOnly = true)
    public Map<LocalDate, List<Long>> readJobPostIdsByMonth(int year, int month) {
        // 해당 연도와 월에 해당하는 LocalDate 범위를 생성
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        LocalDate endOfMonth = startOfMonth.withDayOfMonth(startOfMonth.lengthOfMonth());

        // 특정 월에 해당하는 Schedule들을 필터링하고, 날짜별로 JobPost ID를 매핑
        return scheduleRepository.findAll() // 페이징이 필요 없으므로 findAll() 사용
            .stream()
            .filter(schedule -> {
                LocalDate date = schedule.getCalender();
                return !date.isBefore(startOfMonth) && !date.isAfter(endOfMonth); // 해당 월에 속하는 Schedule만 필터링
            })
            .collect(Collectors.groupingBy(
                Schedule::getCalender, // LocalDate를 기준으로 그룹화
                Collectors.mapping(schedule -> schedule.getJobPost().getId(), Collectors.toList()) // JobPost ID를 리스트로 매핑
            ));
    }

    private JobPostReadServiceResponseDto readDto(JobPost jobPost) {
        return JobPostReadServiceResponseDto.builder()
            .id(jobPost.getId())
            .title(jobPost.getTitle())
            .gatheringLocation(jobPost.getGatheringLocation())
            .gatheringTime(jobPost.getGatheringTime())
            .status(jobPost.getStatus())
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
            .checkTattooList(roleList(jobPost.getId())
                .stream()
                .map(Role::getCheckTattoo)
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

    private List<Role> roleList(Long jobPost_id) {
        return roleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
    }

    private List<Schedule> scheduleList(Long jobPost_id) {
        return scheduleRepository.findByJobPostId(jobPost_id)
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
    }
}
