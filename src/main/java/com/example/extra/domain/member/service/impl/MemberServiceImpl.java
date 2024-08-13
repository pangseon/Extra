package com.example.extra.domain.member.service.impl;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.exception.AccountErrorCode;
import com.example.extra.domain.account.exception.AccountException;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.domain.member.dto.service.request.MemberCreateServiceRequestDto;
import com.example.extra.domain.member.dto.service.response.MemberReadServiceResponseDto;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.mapper.entity.MemberEntityMapper;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.member.service.MemberService;
import com.example.extra.domain.tattoo.dto.service.request.TattooCreateServiceRequestDto;
import com.example.extra.domain.tattoo.entity.Tattoo;
import com.example.extra.domain.tattoo.mapper.entity.TattooEntityMapper;
import com.example.extra.domain.tattoo.repository.TattooRepository;
import com.example.extra.global.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final TattooRepository tattooRepository;
    private final AccountRepository accountRepository;

    // mapper
    private final MemberEntityMapper memberEntityMapper;
    private final TattooEntityMapper tattooEntityMapper;

    @Override
    @Transactional
    public void signup(
        final MemberCreateServiceRequestDto memberCreateServiceRequestDto,
        final TattooCreateServiceRequestDto tattooCreateServiceRequestDto
    ) {
        Account account = accountRepository.findById(memberCreateServiceRequestDto.accountId())
            .orElseThrow(() -> new AccountException(AccountErrorCode.NOT_FOUND_ACCOUNT));

        // Account 권한이 개인 회원자가 아닌 경우 -> throw error
        if (!account.getUserRole().getAuthority().equals("ROLE_USER")) {
            throw new AccountException(AccountErrorCode.INVALID_ROLE_USER);
        }

        Member member = memberEntityMapper.toMember(memberCreateServiceRequestDto, account);
        Tattoo tattoo = tattooEntityMapper.toTattoo(tattooCreateServiceRequestDto, member);

        tattooRepository.save(tattoo);
        member.updateTattoo(tattoo);
        memberRepository.save(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberReadServiceResponseDto readUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) {
        Member member = findById(userDetails.getMember().getId());
        Tattoo tattoo = member.getTattoo();
        return MemberReadServiceResponseDto.builder()
            .name(member.getName())
            .sex(member.getSex())
            .birthday(member.getBirthday())
            .home(member.getHome())
            .height(member.getHeight())
            .weight(member.getWeight())
            .introduction(member.getIntroduction())
            .license(member.getLicense())
            .pros(member.getPros())
            .face(tattoo.getFace())
            .chest(tattoo.getChest())
            .arm(tattoo.getArm())
            .leg(tattoo.getLeg())
            .shoulder(tattoo.getShoulder())
            .back(tattoo.getBack())
            .hand(tattoo.getHand())
            .feet(tattoo.getFeet())
            .etc(tattoo.getEtc())
            .build();
    }

    @Override
    @Transactional
    public void deleteUser(
        final UserDetailsImpl userDetails,
        final HttpServletRequest request
    ) {
        Member member = findById(userDetails.getMember().getId());
        memberRepository.delete(member);
    }

    private Member findById(Long id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }

    // 테스트용 유저 받아오기
    private Member getMemberForTest() {
        return memberRepository.findById(1L)
            .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_MEMBER));
    }
}
