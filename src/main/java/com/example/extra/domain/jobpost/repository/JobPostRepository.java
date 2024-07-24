package com.example.extra.domain.jobpost.repository;

import com.example.extra.domain.jobpost.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost,Long> {

}
