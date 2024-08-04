package com.example.extra.domain.applicationrequest.repository;

import com.example.extra.domain.applicationrequest.entity.ApplicationRequestCompany;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRequestCompanyRepository extends
    JpaRepository<ApplicationRequestCompany, Long> {

    Optional<ApplicationRequestCompany> findByMemberAndRole(
        Member member,
        Role role
    );
}
