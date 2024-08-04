package com.example.extra.global.security;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.enums.UserRole;
import java.util.ArrayList;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Member member;
    private final Company company;

    // member 가입
    public UserDetailsImpl(Member member) {
        this.member = member;
        this.company = null;
    }

    // company 가입
    public UserDetailsImpl(Company company) {
        this.member = null;
        this.company = company;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = member == null ? this.company.getUser_role() : this.member.getUserRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(simpleGrantedAuthority);
        return authorityList;
    }

    @Override
    public String getPassword() {
        return member == null ? company.getPassword() : member.getPassword();
    }

    @Override
    public String getUsername() {
        return member == null ? company.getEmail() : member.getEmail();
    }
}
