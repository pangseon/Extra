package com.example.extra.domain.costumeapprovalboard.service.impl;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.applicationrequest.entity.ApplicationRequestMember;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestErrorCode;
import com.example.extra.domain.applicationrequest.exception.ApplicationRequestException;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestCompanyRepository;
import com.example.extra.domain.applicationrequest.repository.ApplicationRequestMemberRepository;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardCreateServiceDto;
import com.example.extra.domain.costumeapprovalboard.dto.service.CostumeApprovalBoardExplainUpdateServiceRequestDto;
import com.example.extra.domain.costumeapprovalboard.entity.CostumeApprovalBoard;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardErrorCode;
import com.example.extra.domain.costumeapprovalboard.exception.CostumeApprovalBoardException;
import com.example.extra.domain.costumeapprovalboard.repository.CostumeApprovalBoardRepository;
import com.example.extra.domain.costumeapprovalboard.service.CostumeApprovalBoardService;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.domain.role.exception.NotFoundRoleException;
import com.example.extra.domain.role.exception.RoleErrorCode;
import com.example.extra.domain.role.repository.RoleRepository;
import com.example.extra.global.enums.ApplyStatus;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
            throw new ApplicationRequestException(ApplicationRequestErrorCode.NOT_APPROVED_REQUEST);
        }

        // 이미지 저장 후 경로 가져오기 (메서드 추출, 추후 작성)
        String costumeImageUrl = saveImage(costumeApprovalBoardCreateServiceDto.multipartFile());

        // 의상 승인 게시판 생성하기
        CostumeApprovalBoard costumeApprovalBoard = CostumeApprovalBoard.builder()
            .costumeImageUrl(costumeImageUrl)
            .member(member)
            .role(role)
            .image_explain(costumeApprovalBoardCreateServiceDto.image_explain())
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
        // 설명 수정

        // 이미지 수정

        // 저장
    }

    /**
     * 지원 요청 여부 확인 후, 지원 상태 가져오기
     *
     * @param member
     * @param role
     * @return
     */
    private ApplyStatus getApplyStatus(Member member, Role role) {
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
        String url = "이미지 경로";
        return url;
    }
}
