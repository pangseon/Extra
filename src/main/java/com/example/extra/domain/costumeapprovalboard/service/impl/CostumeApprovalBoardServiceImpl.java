package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementReadServiceResponseDto;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.role.entity.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CostumeApprovalBoardServiceImpl implements CostumeApprovalBoardService {
    private final CostumeApprovalBoardRepository costumeApprovalBoardRepository;
    private final JobPostRepository jobPostRepository;
    @Override
    @Transactional(readOnly = true)
    public List<CostumeApprovalBoardCompanyReadServiceResponseDto> getCostumeApprovalBoardForCompany(
        final Company company,
        final Long jobPostId
    ) {
        // 해당 업체가 올린 공고가 아니면 예외 처리
        JobPost jobPost = jobPostRepository.findById(jobPostId)
            .orElseThrow(()->new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        if(!Objects.equals(jobPost.getCompany().getId(), jobPostId)){
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD);
        }

        return jobPost.getRoleList().stream()
                .map(costumeApprovalBoardRepository::findAllByRole)
                .filter(Optional::isPresent)
                .flatMap(costumeApprovalBoardList -> costumeApprovalBoardList.get().stream())
                .map(CostumeApprovalBoardCompanyReadServiceResponseDto::from)
                .collect(Collectors.toList());
    }
}
