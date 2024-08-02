package com.example.extra.domain.role.repository;

import com.example.extra.domain.company.entity.Company;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.role.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findAllByJobPost(JobPost jobPost);
}