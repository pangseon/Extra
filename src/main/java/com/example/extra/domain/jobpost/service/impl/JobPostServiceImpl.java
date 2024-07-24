package com.example.extra.domain.jobpost.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.dto.service.JobPostCreateServiceRequestDto;
import com.example.extra.domain.jobpost.entity.JobPost;
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
}
