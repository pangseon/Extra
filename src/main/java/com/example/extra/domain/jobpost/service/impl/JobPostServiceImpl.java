package com.example.extra.domain.jobpost.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.dto.service.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.dto.service.JobPostUpdateServiceRequestDto;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.mapper.service.JobPostEntityMapper;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.jobpost.service.JobPostService;
import com.example.extra.domain.role.dto.service.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.mapper.service.RoleEntityMapper;
import com.example.extra.sample.exception.NotFoundTestException;
import com.example.extra.sample.exception.TestErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JobPostServiceImpl implements JobPostService {
    private final JobPostRepository jobPostRepository;
    private final JobPostEntityMapper jobPostEntityMapper;
    private final CompanyRepository companyRepository;
    private final RoleEntityMapper roleEntityMapper;

    public void createJobPost(
        final JobPostCreateServiceRequestDto jobPostCreateServiceRequestDto,
        final List<RoleCreateServiceRequestDto> roleCreateServiceRequestDtoList
    ){
        // Test를 위한 Company 사용
        Company company = companyRepository.findById(1L).orElseThrow(
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST));
        System.out.println(company.getName());
        JobPost jobPost = jobPostEntityMapper.toJobPost(jobPostCreateServiceRequestDto,company);
        List<Role> roleList = new ArrayList<>();

        for (int i = 0; i < roleCreateServiceRequestDtoList.size();i++ ){
            roleList.add(roleEntityMapper.toRole(roleCreateServiceRequestDtoList.get(i), jobPost));
        }
        jobPost.getRoleList().addAll(roleList);
        jobPostRepository.save(jobPost);
    }
    @Transactional
    public void updateJobPost(
        Long jobPost_id,
        final JobPostUpdateServiceRequestDto jobPostUpdateServiceRequestDto
        // ,Company company
    ){
        Company company = companyRepository.findById(1L).orElseThrow(
            ()-> new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST));
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPost.updateJobPost(
            jobPostUpdateServiceRequestDto.dramaTitle(),
            jobPostUpdateServiceRequestDto.gatheringLocation(),
            jobPostUpdateServiceRequestDto.gatheringTime(),
            jobPostUpdateServiceRequestDto.status(),
            jobPostUpdateServiceRequestDto.hourPay(),
            jobPostUpdateServiceRequestDto.category());
        jobPostRepository.save(jobPost);
    }
    public void deleteJobPost(
        Long jobPost_id
        //,Company company
    ){
        Company company = companyRepository.findById(1L)
            .orElseThrow(()->new NotFoundTestException(TestErrorCode.NOT_FOUND_TEST));
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()-> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        jobPostRepository.delete(jobPost);
    }
}
