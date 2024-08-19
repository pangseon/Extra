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
import com.example.extra.domain.role.dto.service.response.RoleCreateServiceResponseDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceResponseDto;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.RoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.mapper.service.RoleEntityMapper;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.role.service.RoleService;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.exception.TattooErrorCode;
import com.example.extra.domain.tattoo.exception.TattooException;
import com.example.extra.domain.tattoo.repository.TattooRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final JobPostRepository jobPostRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final RoleRepository roleRepository;
    private final CompanyRepository companyRepository;
    private final TattooRepository tattooRepository;

    @Override
    @Transactional
    public RoleCreateServiceResponseDto createRole(
        Long jobPostId,
        final Account account,
        final RoleCreateServiceRequestDto roleCreateServiceRequestDto
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPostId,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleEntityMapper.toRole(roleCreateServiceRequestDto, jobPost);
        Tattoo tattoo = tattooRepository.findByTattooCreateControllerRequestDto(
                roleCreateServiceRequestDto.tattoo())
            .orElseThrow(() -> new TattooException(TattooErrorCode.NOT_FOUND_TATTOO));
        role.updateTattoo(tattoo);
        role = roleRepository.save(role);
        return new RoleCreateServiceResponseDto(role.getId());
    }
    @Override
    @Transactional
    public void updateRole(
        Long jobPostId,
        Long roleId,
        final Account account,
        final RoleUpdateServiceRequestDto roleUpdateServiceRequestDto
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPostId,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(roleId, jobPost)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
        Tattoo tattoo = tattooRepository.findByTattooCreateControllerRequestDto(
                roleUpdateServiceRequestDto.tattoo())
            .orElseThrow(() -> new TattooException(TattooErrorCode.NOT_FOUND_TATTOO));
        role.updateRole(
            roleUpdateServiceRequestDto.roleName(),
            roleUpdateServiceRequestDto.costume(),
            roleUpdateServiceRequestDto.sex(),
            roleUpdateServiceRequestDto.minAge(),
            roleUpdateServiceRequestDto.maxAge(),
            roleUpdateServiceRequestDto.limitPersonnel(),
            roleUpdateServiceRequestDto.currentPersonnel(),
            roleUpdateServiceRequestDto.season(),
            tattoo
        );
    }
    @Override
    @Transactional
    public void deleteRole(
        Long jobPostId,
        Long roleId,
        final Account account
    ) {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPostId,
                getCompanyByAccount(account))
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(roleId, jobPost)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
        roleRepository.delete(role);
    }
    @Override
    @Transactional(readOnly = true)
    public RoleServiceResponseDto readRole(
        Long jobPostId,
        Long roleId
    ) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(roleId, jobPost)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
        int minAge = AgeCalculator(LocalDate.now(),role.getMinAge());
        int maxAge = AgeCalculator(LocalDate.now(),role.getMaxAge());
        return RoleServiceResponseDto.from(role,minAge,maxAge);
    }
    @Override
    @Transactional(readOnly = true)
    public List<RoleServiceResponseDto> readAllRole(Long jobPostId) {
        JobPost jobPost = jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        return roleRepository.findAllByJobPost(jobPost)
            .stream()
            .map(role -> {
                int minAge = AgeCalculator(LocalDate.now(), role.getMinAge());
                int maxAge = AgeCalculator(LocalDate.now(), role.getMaxAge());
                return RoleServiceResponseDto.from(role,minAge,maxAge);
            })
            .toList();
    }
    private Integer AgeCalculator(LocalDate localDateNow,LocalDate localDate){
        return Period.between(localDate,localDateNow).getYears();
    }
    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }
}
