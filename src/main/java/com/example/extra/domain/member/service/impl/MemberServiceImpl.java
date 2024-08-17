package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.request.MemberUpdateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.mapper.entity.MemberEntityMapper;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.controller.TattooCreateControllerRequestDto;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.repository.TattooRepository;
import com.example.extra.global.s3.S3Provider;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TattooRepository tattooRepository;
    private final AccountRepository accountRepository;

    // mapper
    private final MemberEntityMapper memberEntityMapper;

    private final S3Provider s3Provider;

    @Override
    @Transactional
    public void signup(final MemberCreateServiceRequestDto memberCreateServiceRequestDto) {
        Account account = accountRepository.findById(memberCreateServiceRequestDto.accountId())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));

        // 이미 회원 가입한 계정
        memberRepository.findByAccount(account)
            .ifPresent(a -> {
                throw new AccountException(AccountErrorCode.DUPLICATION_ACCOUNT);
            });

        // Account 권한이 개인 회원자가 아닌 경우 -> throw error
        if (!account.getUserRole().getAuthority().equals("ROLE_USER")) {
            throw new AccountException(AccountErrorCode.INVALID_ROLE_USER);
        }

        Member member = memberEntityMapper.toMember(memberCreateServiceRequestDto, account);
        Tattoo tattoo = tattooRepository.findByTattooCreateControllerRequestDto(
                memberCreateServiceRequestDto.tattoo())
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_TATTOO));

        member.updateTattoo(tattoo);
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberReadServiceResponseDto readOnce(
        final Account account,
        final HttpServletRequest request
    ) {
        Member member = findByAccount(account);
        return MemberReadServiceResponseDto.from(member);
    }

    @Override
    @Transactional
    public void update(
        final Account account,
        final MemberUpdateServiceRequestDto memberUpdateServiceRequestDto,
        final MultipartFile multipartFile
    ) throws IOException {
        Member member = findByAccount(account);
        // member update
        if (memberUpdateServiceRequestDto != null) {
            member.update(memberUpdateServiceRequestDto);

            // tattoo update
            if (memberUpdateServiceRequestDto.tattoo() != null){
                member.updateTattoo(getTattoo(memberUpdateServiceRequestDto.tattoo()));
            }

            // 프로필 이미지 수정
            if (memberUpdateServiceRequestDto.isImageChange()) {
                String imageUrl = s3Provider.updateImage(
                    account.getImageUrl(),
                    account.getFolderUrl(),
                    multipartFile
                );
                account.updateImageUrl(imageUrl);
            }
        }
    }

    private Tattoo getTattoo(final TattooCreateControllerRequestDto tattooCreateControllerRequestDto) {
        return tattooRepository.findByTattooCreateControllerRequestDto(tattooCreateControllerRequestDto)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_TATTOO));
    }

    private Member findByAccount(final Account account) {
        return memberRepository.findByAccount(account)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    @Override
    @Transactional
    public void delete(final Account account) {
        Member member = findByAccount(account);
        memberRepository.delete(member);
    }
}
