package com.example.extra.global.security;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("사용자 아이디 " + username);

        // member 확인
        Member member = memberRepository.findByEmail(username)
            .orElse(null);

        // company 가입 일 경우
        if (member == null) {
            Company company = companyRepository.findByEmail(username)
                .orElseThrow(() -> new CompanyException(CompanyErrorCode.NOT_FOUND_COMPANY));
            return new UserDetailsImpl(company);
        }

        return new UserDetailsImpl(member);
    }
}
