package com.example.extra.domain.schedule.repository;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.entity.Schedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<List<Schedule>> findByJobPostId(Long id);

    Optional<Schedule> findByIdAndJobPost(Long id, JobPost jobPost);
}
