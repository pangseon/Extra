package com.example.extra.domain.role.service.impl;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.role.dto.service.request.RoleCreateServiceRequestDto;
import com.example.extra.domain.role.dto.service.request.RoleUpdateServiceRequestDto;
import com.example.extra.domain.role.dto.service.response.RoleServiceReResponseDto;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.mapper.service.RoleEntityMapper;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.domain.role.service.RoleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final JobPostRepository jobPostRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final RoleRepository roleRepository;

    public void createRole(
        Long jobPost_id,
        Company company,
        RoleCreateServiceRequestDto roleCreateServiceRequestDto)
    {
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleEntityMapper.toRole(roleCreateServiceRequestDto,jobPost);
        roleRepository.save(role);
    }
    public void updateRole(
        Long jobPost_id,
        Long role_id,
        Company company,
        RoleUpdateServiceRequestDto roleUpdateServiceRequestDto
    ){
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id,jobPost)
            .orElseThrow(()->new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        role.updateRole(
            roleUpdateServiceRequestDto.role_name(),
            roleUpdateServiceRequestDto.costume(),
            roleUpdateServiceRequestDto.sex(),
                roleUpdateServiceRequestDto.role_age(),
            roleUpdateServiceRequestDto.limit_personnel(),
            roleUpdateServiceRequestDto.current_personnel(),
            roleUpdateServiceRequestDto.season(),
            roleUpdateServiceRequestDto.check_tattoo());
        roleRepository.save(role);
    }
    public void deleteRole(
        Long jobPost_id,
        Long role_id,
        Company company
    ){
        JobPost jobPost = jobPostRepository.findByIdAndCompany(jobPost_id,company)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id,jobPost)
            .orElseThrow(()->new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        roleRepository.delete(role);
    }
    public RoleServiceReResponseDto readRole(
        Long jobPost_id,
        Long role_id
    ){
        JobPost jobPost = jobPostRepository.findById(jobPost_id)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        Role role = roleRepository.findByIdAndJobPost(role_id,jobPost)
            .orElseThrow(()->new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));
        return roleEntityMapper.toRoleServiceReResponseDto(role);
    }
    public List<RoleServiceReResponseDto> readAllRole(Long jobPost_id){
        JobPost jobPost =jobPostRepository.findById(jobPost_id)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        List<Role> roleList = roleRepository.findAllByJobPost(jobPost);
        return roleEntityMapper.toListRoleServiceReResponseDto(roleList);
    }
}
