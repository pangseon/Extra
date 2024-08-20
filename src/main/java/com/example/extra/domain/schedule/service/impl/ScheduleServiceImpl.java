package com.example.extra.domain.schedule.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleCreateServiceResponseDto;
import com.example.extra.domain.schedule.dto.service.response.ScheduleReadServiceResponseDto;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
import com.example.extra.domain.schedule.exception.ScheduleException;
import com.example.extra.domain.schedule.mapper.service.ScheduleEntityMapper;
import com.example.extra.domain.schedule.repository.ScheduleRepository;
import com.example.extra.domain.schedule.service.ScheduleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {

    // repository
    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;
    private final ScheduleRepository scheduleRepository;

    // mapper
    private final ScheduleEntityMapper scheduleEntityMapper;

    @Override
    @Transactional
    public ScheduleCreateServiceResponseDto createSchedule(
        final Account account,
        final Long jobPost_id,
        final ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto
    ) {
        JobPost jobPost = getJobPostByAccountAndId(
            account,
            jobPost_id
        );

        Schedule schedule = scheduleEntityMapper.toSchedule(
            scheduleCreateServiceRequestDto,
            jobPost
        );
        schedule = scheduleRepository.save(schedule);

        return new ScheduleCreateServiceResponseDto(schedule.getId());
    }

    @Override
    @Transactional
    public void updateSchedule(
        final Account account,
        final Long jobPost_id,
        final Long schedule_id,
        final ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    ) {
        JobPost jobPost = getJobPostByAccountAndId(
            account,
            jobPost_id
        );
        Schedule schedule = getScheduleByIdAndJobPost(schedule_id, jobPost);
        schedule.updateSchedule(scheduleUpdateServiceRequestDto.calender());

        scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(
        final Account account,
        final Long jobPost_id,
        final Long schedule_id
    ) {
        JobPost jobPost = getJobPostByAccountAndId(
            account,
            jobPost_id
        );
        Schedule schedule = getScheduleByIdAndJobPost(schedule_id, jobPost);

        scheduleRepository.delete(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleReadServiceResponseDto readSchedule(
        final Long jobPost_id,
        final Long schedule_id
    ) {
        JobPost jobPost = getJobPostById(jobPost_id);
        Schedule schedule = getScheduleByIdAndJobPost(schedule_id, jobPost);

        return scheduleEntityMapper.toScheduleServiceResponseDto(schedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleReadServiceResponseDto> readAllSchedule(Long jobPost_id) {
        JobPost jobPost = getJobPostById(jobPost_id);
        List<Schedule> scheduleList = scheduleRepository.findByJobPostId(jobPost.getId())
            .orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));

        return scheduleEntityMapper.toListScheduleServiceResponseDto(scheduleList);
    }

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    private JobPost getJobPostByAccountAndId(
        final Account account,
        final Long jobPost_id
    ) {
        return jobPostRepository.findByIdAndCompany(
            jobPost_id,
            getCompanyByAccount(account)
        ).orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
    }

    private Schedule getScheduleByIdAndJobPost(final Long schedule_id, final JobPost jobPost) {
        return scheduleRepository.findByIdAndJobPost(
            schedule_id,
            jobPost
        ).orElseThrow(() -> new ScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
    }

    private JobPost getJobPostById(final Long jobPost_id) {
        return jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
    }
}
