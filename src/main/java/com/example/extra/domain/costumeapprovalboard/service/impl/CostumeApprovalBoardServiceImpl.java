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
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardApplyStatusUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardExplainCreateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.request.CostumeApprovalBoardUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadDetailServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardCompanyReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.response.CostumeApprovalBoardMemberReadServiceResponseDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.exception.RoleException;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import com.example.extra.global.s3.S3Provider;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        return CostumeApprovalBoardMemberReadServiceResponseDto.from(
            costumeApprovalBoard,
            s3Provider.getCostumeImagePresignedUrl(account.getId(), costumeApprovalBoard.getId())
        );
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
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
        if (!Objects.equals(role.getJobPost().getCompany().getId(), getCompanyByAccount(account).getId())) {
            throw new CostumeApprovalBoardException(
                CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD);
        }

        Slice<CostumeApprovalBoard> costumeApprovalBoardSlice;
        costumeApprovalBoardSlice = (memberName != null)
            ? costumeApprovalBoardRepository.findAllByRoleAndMember_NameContaining(role, memberName, pageable)
            : costumeApprovalBoardRepository.findAllByRole(role, pageable);

        return costumeApprovalBoardSlice.stream()
            .map(costumeApprovalBoard -> CostumeApprovalBoardCompanyReadServiceResponseDto.from(
                costumeApprovalBoard,
                s3Provider.getCostumeImagePresignedUrl(costumeApprovalBoard.getMember().getAccount().getId(), costumeApprovalBoard.getId()))
            ).toList();
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
        return CostumeApprovalBoardCompanyReadDetailServiceResponseDto.from(
            costumeApprovalBoard,
            s3Provider.getCostumeImagePresignedUrl(costumeApprovalBoard.getMember().getAccount().getId(), costumeApprovalBoard.getId())
        );
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
        CostumeApprovalBoardExplainCreateServiceRequestDto costumeApprovalBoardExplainCreateServiceRequestDto,
        MultipartFile multipartFile
    ) {
        Member member = getMemberByAccount(account);
        Role role = roleRepository.findById(roleId)
            .orElseThrow(() -> new RoleException(RoleErrorCode.NOT_FOUND_ROLE));
        // 이미 의상 승인 글을 작성한 경우
        costumeApprovalBoardRepository.findByMemberAndRole(
                member,
                role
            ).ifPresent(c -> {
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

        // 의상 승인 게시판 생성
        CostumeApprovalBoard costumeApprovalBoard = CostumeApprovalBoard.builder()
                .costumeImageUrl("temp") // TODO - 삭제
                .member(member)
                .role(role)
                .imageExplain(costumeApprovalBoardExplainCreateServiceRequestDto.imageExplain())
                .folderName("temp") // TODO - 삭제
            .build();
        Long costumeApprovalBoardId =
            costumeApprovalBoardRepository.save(costumeApprovalBoard)
                .getId();

        // S3 이미지 업로드
        Long accountId = member.getAccount().getId();
        s3Provider.saveCostumeApprovalBoardImage(
            accountId.toString(),
            costumeApprovalBoardId.toString(),
            multipartFile
        );
    }

    @Override
    @Transactional
    public void updateCostumeApprovalBoardByMember(
        final Long costumeApprovalBoardId,
        final Account account,
        final CostumeApprovalBoardUpdateServiceRequestDto serviceRequestDto,
        final MultipartFile multipartFile
    ) {
        Member member = getMemberByAccount(account);
        CostumeApprovalBoard costumeApprovalBoard =
            costumeApprovalBoardRepository.findByIdAndMember(costumeApprovalBoardId,member)
                .orElseThrow(()->new CostumeApprovalBoardException(CostumeApprovalBoardErrorCode.NOT_ABLE_TO_ACCESS_COSTUME_APPROVAL_BOARD));
        // 의상 이미지 수정
        if (serviceRequestDto.isImageUpdate()){
            s3Provider.updateCostumeApprovalBoardImage(serviceRequestDto.imageUrl(), multipartFile);
        }
        // imageExplain 수정
        costumeApprovalBoard.updateImageExplain(serviceRequestDto.imageExplain());
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
}
