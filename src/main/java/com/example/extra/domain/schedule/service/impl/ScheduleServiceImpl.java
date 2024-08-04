package com.example.extra.domain.schedule.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.schedule.dto.service.request.ScheduleCreateServiceRequestDto;
import com.example.extra.domain.schedule.dto.service.request.ScheduleUpdateServiceRequestDto;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.domain.schedule.exception.NotFoundScheduleException;
import com.example.extra.domain.schedule.exception.ScheduleErrorCode;
import com.example.extra.domain.schedule.mapper.service.ScheduleEntityMapper;
import com.example.extra.domain.schedule.repository.ScheduleRepository;
import com.example.extra.domain.schedule.service.ScheduleService;
import com.example.extra.sample.exception.NotFoundTestException;
import com.example.extra.sample.exception.TestErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final JobPostRepository jobPostRepository;
    private final ScheduleEntityMapper scheduleEntityMapper;
    private final CompanyRepository companyRepository;
    private final ScheduleRepository scheduleRepository;

    public void createSchedule(
        Long jobPost_id,
        Company company,
        ScheduleCreateServiceRequestDto scheduleCreateServiceRequestDto
    ){
        JobPost jobPost = jobPostRepository.findByIdAndCompany(
            jobPost_id,
            company
        ).orElseThrow(()-> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleEntityMapper.toSchedule(scheduleCreateServiceRequestDto,jobPost);
        scheduleRepository.save(schedule);

    }
    public void updateSchedule(
        Long jobPost_id,
        Long schedule_id,
        Company company,
        ScheduleUpdateServiceRequestDto scheduleUpdateServiceRequestDto
    ){
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleRepository.findByIdAndJobPost(schedule_id,jobPost)
            .orElseThrow(()->new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        schedule.updateSchedule(scheduleUpdateServiceRequestDto.calender());
        scheduleRepository.save(schedule);
    }
    public void deleteSchedule(
        Long jobPost_id,
        Long schedule_id,
        Company company
    ){
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Schedule schedule = scheduleRepository.findByIdAndJobPost(schedule_id,jobPost)
            .orElseThrow(()->new NotFoundScheduleException(ScheduleErrorCode.NOT_FOUND_SCHEDULE));
        scheduleRepository.delete(schedule);
    }

}
