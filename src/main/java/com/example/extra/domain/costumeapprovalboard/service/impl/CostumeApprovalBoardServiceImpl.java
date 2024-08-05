package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestException;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestCompanyRepository;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.jobpost.repository.JobPostRepository;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CostumeApprovalBoardServiceImpl implements CostumeApprovalBoardService {

    private final CostumeApprovalBoardRepository costumeApprovalBoardRepository;
    private final JobPostRepository jobPostRepository;
    private final RoleRepository roleRepository;
    private final ApplicationRequestMemberRepository applicationRequestMemberRepository;
    private final ApplicationRequestCompanyRepository applicationRequestCompanyRepository;

    public CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        Member member,
        Long roleId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findByMemberAndRoleId(
                member, roleId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        return CostumeApprovalBoardMemberReadServiceResponseDto.from(costumeApprovalBoard);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CostumeApprovalBoardCompanyReadServiceResponseDto> getCostumeApprovalBoardForCompany(
        final Company company,
        final Long jobPostId
    ) {
        // 해당 업체가 올린 공고가 아니면 예외 처리
        JobPost jobPost = jobPostRepository.findById(jobPostId)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        if (!Objects.equals(jobPost.getCompany().getId(), jobPostId)) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD);
        }

        return jobPost.getRoleList().stream()
            .map(costumeApprovalBoardRepository::findAllByRole)
            .filter(Optional::isPresent)
            .flatMap(costumeApprovalBoardList -> costumeApprovalBoardList.get().stream())
            .map(CostumeApprovalBoardCompanyReadServiceResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        final Company company,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(),
            company.getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_READ_COSTUME_APPROVAL_BOARD);
        }
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto.from(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void deleteCostumeApprovalBoardByMember(
        Member member,
        Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getMember().getId(), member.getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void deleteCostumeApprovalBoardByCompany(
        final Company company,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(),
            company.getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void createCostumeApprovalBoard(
        Long roleId,
        Member member,
        CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto
    ) {
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new NotFoundRoleException(RoleErrorCode.NOT_FOUND_ROLE));

        // 이미 의상 승인 글을 작성한 경우
        costumeApprovalBoardRepository.findByMemberAndRole(
                member,
                role)
            .ifPresent(c -> {
                throw new CostumeApprovalBoardException(
                    CostumeApprovalBoardErrorCode.ALREADY_EXIST_COSTUME_APPROVAL_BOARD);
            });

        /**
         *  member가 승인 받은 공고 & 역할이 맞는지 확인
         *  [지원요청 개인 | 지원요청 하청]에서 개인과 역할을 통해서 검색 후, apply status 확인
         *  - 해당 역할에 지원요청 하지 않은 경우 -> throw 지원 요청 예외
         *  - 해당 역할 승인을 받지 못한 경우 -> throw 미승인 예외
         */
        if (getApplyStatus(member, role) != ApplyStatus.APPROVED) {
            throw new ApplicationRequestException(
                ApplicationRequestErrorCode.NOT_APPROVED_REQUEST);
        }

        // 이미지 저장 후 경로 가져오기 (메서드 추출, 추후 작성)
        String costumeImageUrl = saveImage(
            costumeApprovalBoardCreateServiceDto.multipartFile());

        // 의상 승인 게시판 생성하기
        CostumeApprovalBoard costumeApprovalBoard = CostumeApprovalBoard.builder()
            .costumeApprove(false)
            .costumeImageUrl(costumeImageUrl)
            .member(member)
            .role(role)
            .imageExplain(costumeApprovalBoardCreateServiceDto.image_explain())
            .build();
        costumeApprovalBoardRepository.save(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Member member,
        final CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto
    ) {
        CostumeApprovalBoard costumeApprovalBoard =
            costumeApprovalBoardRepository.findById(costumeApprovalBoardId)
                .orElseThrow(() -> new CostumeApprovalBoardException(
                    CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD));

        // 회원이 작성한 글이 아님 -> throw 주인 아님 예외
        if (!member.getId().equals(costumeApprovalBoard.getMember().getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_MASTER_COSTUME_APPROVAL_BOARD);
        }

        // 아무것도 들어오지 않았을 경우
        if (serviceRequestDto == null) {
            return;
        }

        // 설명 수정
        if (serviceRequestDto.image_explain() != null) {
            costumeApprovalBoard.updateImageExplain(serviceRequestDto.image_explain());
        }

        // 이미지 수정
        if (serviceRequestDto.multipartFile() != null) {
            String url = saveImage(serviceRequestDto.multipartFile());
            costumeApprovalBoard.updateCostumeImageUrl(url);
        }
    }

    public void updateCostumeApprovalBoardByCompany(
        Company company,
        Long costumeApprovalBoardId
    ) {
        // costume approval board가 있는지 확인
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId).
            orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD));

        // company가 작성한 job post의 role인지 확인
        if (!company.getId().equals(
            costumeApprovalBoard
                .getRole()
                .getJobPost()
                .getCompany()
                .getId())
        ) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD);
        }

        // 승인 여부 변경
        costumeApprovalBoard.updateCostumeApprove();
    }

    /**
     * 지원 요청 여부 확인 후, 지원 상태 가져오기
     *
     * @param member
     * @param role
     * @return
     */
    private ApplyStatus getApplyStatus(
        Member member,
        Role role
    ) {
        Optional<ApplicationRequestMember> requestMemberOptional =
            applicationRequestMemberRepository.findByMemberAndRole(
                member,
                role
            );
        Optional<ApplicationRequestCompany> requestCompanyOptional =
            applicationRequestCompanyRepository.findByMemberAndRole(
                member,
                role
            );

        if (requestMemberOptional.isEmpty()
        ) {
            if (requestCompanyOptional.isEmpty()) {
                throw new ApplicationRequestException(
                    ApplicationRequestErrorCode.NOT_FOUND_APPLICATION_REQUEST);
            }
            return requestCompanyOptional.get().getApplyStatus();
        } else {
            return requestMemberOptional.get().getApplyStatus();
        }
    }

    private String saveImage(MultipartFile multipartFile) {
        String url = multipartFile.getOriginalFilename();
        return url;
    }
}
