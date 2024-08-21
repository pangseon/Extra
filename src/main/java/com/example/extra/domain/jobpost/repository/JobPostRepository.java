package com.example.extra.domain.jobpost.repository;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.entity.JobPost;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    Optional<JobPost> findByIdAndCompany(Long id, Company company);
    List<JobPost> findByCompany(PageRequest pageRequest,Company company);

}
