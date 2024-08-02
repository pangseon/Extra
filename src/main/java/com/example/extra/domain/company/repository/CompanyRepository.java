package com.example.extra.domain.company.repository;

import com.example.extra.domain.company.entity.Company;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findById(Long id);

    Optional<Company> findByEmail(String email);
}
