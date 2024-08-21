package com.example.extra.domain.schedule.repository;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.entity.Schedule;
import io.lettuce.core.dynamic.annotation.Param;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<List<Schedule>> findByJobPostId(Long id);

    Optional<Schedule> findByIdAndJobPost(Long id, JobPost jobPost);

    @Query("SELECT s FROM Schedule s WHERE FUNCTION('YEAR', s.calender) = :year AND FUNCTION('MONTH', s.calender) = :month")
    List<Schedule> findByCalenderYearAndCalenderMonth(@Param("year") int year,
        @Param("month") int month, Pageable pageable);
}
