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
import com.example.extra.domain.schedule.dto.service.response.ScheduleServiceResponseDto;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.NotFoundScheduleException;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
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

    private final JobPostRepository jobPostRepository;
    private final ScheduleEntityMapper scheduleEntityMapper;
    private final CompanyRepository companyRepository;
    private final ScheduleRepository scheduleRepository;

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }
    @Override
    @Transactional
    public void createSchedule(
        Long jobPost_id,
        final Account account,
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(
            jobPost_id,
            getCompanyByAccount(account)
        ).orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleEntityMapper.toSchedule(scheduleCreateServiceRequestDto,
            jobPost);
        scheduleRepository.save(schedule);

    }
    @Override
    @Transactional
    public void updateSchedule(
        Long jobPost_id,
        Long schedule_id,
        final Account account,
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleRepository.findByIdAndJobPost(schedule_id, jobPost)
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        schedule.updateSchedule(scheduleUpdateServiceRequestDto.calender());
        scheduleRepository.save(schedule);
    }
    @Override
    @Transactional
    public void deleteSchedule(
        Long jobPost_id,
        Long schedule_id,
        final Account account
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleRepository.findByIdAndJobPost(schedule_id, jobPost)
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        scheduleRepository.delete(schedule);
    }
    @Override
    @Transactional(readOnly = true)
    public ScheduleServiceResponseDto readSchedule(
        Long jobPost_id,
        Long schedule_id) {
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleRepository.findByIdAndJobPost(schedule_id, jobPost)
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        return scheduleEntityMapper.toScheduleServiceResponseDto(schedule);
    }
    @Override
    @Transactional(readOnly = true)
    public List<ScheduleServiceResponseDto> readAllSchedule(Long jobPost_id) {
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        List<Schedule> scheduleList = scheduleRepository.findByJobPostId(jobPost.getId())
            .orElseThrow(() -> new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        return scheduleEntityMapper.toListScheduleServiceResponseDto(scheduleList);
    }
}
