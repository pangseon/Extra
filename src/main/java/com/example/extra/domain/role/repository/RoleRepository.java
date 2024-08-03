package com.example.extra.domain.role.repository;

import com.example.extra.domain.role.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<List<Role>> findByJobPostId(Long id);

}
