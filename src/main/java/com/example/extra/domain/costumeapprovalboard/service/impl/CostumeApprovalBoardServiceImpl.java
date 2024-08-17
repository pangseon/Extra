package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestException;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestCompanyRepository;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardApplyStatusUpdateServiceRequestDto;
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
import com.example.extra.domain.jobpost.exception.JobPostErrorCode;
import com.example.extra.domain.jobpost.exception.NotFoundJobPostException;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.RoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.s3.S3Provider;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class CostumeApprovalBoardServiceImpl implements CostumeApprovalBoardService {

    private final CostumeApprovalBoardRepository costumeApprovalBoardRepository;
    private final RoleRepository roleRepository;
    private final ApplicationRequestMemberRepository applicationRequestMemberRepository;
    private final ApplicationRequestCompanyRepository applicationRequestCompanyRepository;
    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final S3Provider s3Provider;
    private final String s3url = "https://light-house-ai.s3.ap-northeast-2.amazonaws.com/";
    private final String SEPARATOR = "/";

    private Member getMemberByAccount(final Account account) {
        return memberRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }

    private Company getCompanyByAccount(final Account account) {
        return companyRepository.findByAccount(account)
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));
    }
    @Transactional(readOnly = true)
    public CostumeApprovalBoardMemberReadServiceResponseDto getCostumeApprovalBoardForMember(
        final Account account,
        final Long roleId
    ) {
        Member member = getMemberByAccount(account);
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
        final Account account,
        final Long roleId,
        final String memberName,
        final Pageable pageable
    ) {
        // 해당 업체가 올린 공고가 아니면 예외 처리
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new NotFoundJobPostException(JobPostErrorCode.NOT_FOUND_JOBPOST));
        if (!Objects.equals(role.getJobPost().getCompany().getId(), getCompanyByAccount(account).getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD);
        }

        Slice<CostumeApprovalBoard> costumeApprovalBoardSlice;
        costumeApprovalBoardSlice = (memberName != null)
            ? costumeApprovalBoardRepository.findAllByRoleAndMember_NameContaining(role, memberName, pageable)
            : costumeApprovalBoardRepository.findAllByRole(role, pageable);

        return costumeApprovalBoardSlice.stream()
            .map(CostumeApprovalBoardCompanyReadServiceResponseDto::from)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CostumeApprovalBoardCompanyReadDetailServiceResponseDto getCostumeApprovalBoardDetailForCompany(
        final Account account,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(),
            getCompanyByAccount(account).getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_READ_COSTUME_APPROVAL_BOARD);
        }
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto.from(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void deleteCostumeApprovalBoardByMember(
        final Account account,
        Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getMember().getId(),
            getMemberByAccount(account).getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void deleteCostumeApprovalBoardByCompany(
        final Account account,
        final Long costumeApprovalBoardId
    ) {
        CostumeApprovalBoard costumeApprovalBoard = costumeApprovalBoardRepository.findById(
                costumeApprovalBoardId)
            .orElseThrow(() -> new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_FOUND_COSTUME_APPROVAL_BOARD)
            );
        if (!Objects.equals(costumeApprovalBoard.getRole().getJobPost().getCompany().getId(),
            getCompanyByAccount(account).getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_DELETE_COSTUME_APPROVAL_BOARD);
        }
        costumeApprovalBoardRepository.delete(costumeApprovalBoard);
    }

    @Override
    @Transactional
    public void createCostumeApprovalBoard(
        Long roleId,
        final Account account,
        CostumeApprovalBoardCreateServiceDto costumeApprovalBoardCreateServiceDto,
        MultipartFile multipartFile
    ) throws IOException {
        Member member = getMemberByAccount(account);
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
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
        String fileName;
        String fileUrl;
        String folderName = member.getName() + UUID.randomUUID();

        // 이미지 저장 후 경로 가져오기 (메서드 추출, 추후 작성)
        fileName = s3Provider.originalFileName(multipartFile);
        fileUrl = s3url + folderName + SEPARATOR + fileName;

        // 의상 승인 게시판 생성하기
        CostumeApprovalBoard costumeApprovalBoard = CostumeApprovalBoard.builder()
            .costumeImageUrl(fileUrl)
            .member(member)
            .role(role)
            .imageExplain(costumeApprovalBoardCreateServiceDto.imageExplain())
            .folderName(folderName)
            .build();
        costumeApprovalBoardRepository.save(costumeApprovalBoard);
        fileUrl = folderName + SEPARATOR + fileName;
        s3Provider.saveFile(multipartFile, fileUrl);
    }

    @Override
    @Transactional
    public void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Account account,
        final CostumeApprovalBoardExplainUpdateServiceRequestDto serviceRequestDto,
        final MultipartFile multipartFile
    ) throws IOException {
        Member member = getMemberByAccount(account);
        CostumeApprovalBoard costumeApprovalBoard =
            costumeApprovalBoardRepository.findByIdAndMember(costumeApprovalBoardId,member)
                .orElseThrow(()->new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD));
        if (!serviceRequestDto.imageChange()){
            costumeApprovalBoard.updateImageExplain(serviceRequestDto.imageExplain());
            costumeApprovalBoard.updateCostumeImageUrl(costumeApprovalBoard.getCostumeImageUrl());
        }else{
            String imageName = s3Provider.updateImage(costumeApprovalBoard.getCostumeImageUrl(),
                costumeApprovalBoard.getFolderName(),multipartFile);
            costumeApprovalBoard.updateImageExplain(serviceRequestDto.imageExplain());
            costumeApprovalBoard.updateCostumeImageUrl(imageName);
        }

    }

    @Override
    @Transactional
    public void updateCostumeApprovalBoardByCompany(
        final Account account,
        Long costumeApprovalBoardId,
        CostumeApprovalBoardApplyStatusUpdateServiceRequestDto serviceRequestDto
    ) {
        Company company = getCompanyByAccount(account);
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

        // 승인 여부 확인 및 변경
        if (costumeApprovalBoard.getCostumeApprove() == ApplyStatus.APPROVED) {
            throw new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.ALREADY_APPROVED);
        }
        costumeApprovalBoard.updateCostumeApprove(
            serviceRequestDto.costumeApprove()
        );
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
