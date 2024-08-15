package com.example.extra.domain.role.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.mapper.service.RoleEntityMapper;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.role.service.RoleService;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final JobPostRepository jobPostRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    public void createRole(
        Long jobPost_id,
        final Account account,
        RoleCreateServiceRequestDto roleCreateServiceRequestDto) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleEntityMapper.toRole(roleCreateServiceRequestDto, jobPost);
        roleRepository.save(role);
    }

    public void updateRole(
        Long jobPost_id,
        Long role_id,
        final Account account,
        RoleUpdateServiceRequestDto roleUpdateServiceRequestDto
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id, jobPost)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        role.updateRole(
            roleUpdateServiceRequestDto.roleName(),
            roleUpdateServiceRequestDto.costume(),
            roleUpdateServiceRequestDto.sex(),
            roleUpdateServiceRequestDto.minAge(),
            roleUpdateServiceRequestDto.maxAge(),
            roleUpdateServiceRequestDto.limitPersonnel(),
            roleUpdateServiceRequestDto.currentPersonnel(),
            roleUpdateServiceRequestDto.season(),
            roleUpdateServiceRequestDto.checkTattoo());
        roleRepository.save(role);
    }

    public void deleteRole(
        Long jobPost_id,
        Long role_id,
        final Account account
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id, jobPost)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        roleRepository.delete(role);
    }

    public RoleServiceResponseDto readRole(
        Long jobPost_id,
        Long role_id
    ) {
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id, jobPost)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        int minAge = AgeCalculator(LocalDate.now(),role.getMinAge());
        int maxAge = AgeCalculator(LocalDate.now(),role.getMaxAge());
        return roleEntityMapper.toRoleServiceResponseDto(role,minAge,maxAge);
    }
    private Integer AgeCalculator(LocalDate localDateNow,LocalDate localDate){
        return Period.between(localDate,localDateNow).getYears();
    }

    public List<RoleServiceResponseDto> readAllRole(Long jobPost_id) {
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        return roleRepository.findAllByJobPost(jobPost)
            .stream()
            .map(role -> {
                int minAge = AgeCalculator(LocalDate.now(), role.getMinAge());
                int maxAge = AgeCalculator(LocalDate.now(), role.getMaxAge());
                return roleEntityMapper.toRoleServiceResponseDto(role, minAge, maxAge);
            })
            .toList();
    }
}
