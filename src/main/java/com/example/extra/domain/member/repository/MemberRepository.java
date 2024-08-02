package com.example.extra.domain.member.repository;

import com.example.extra.domain.member.entity.Member;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNameAndBirthday(String name, LocalDate birthday);
}
