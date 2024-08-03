package com.example.extra.domain.attendancemanagement.repository;

import com.example.extra.domain.attendancemanagement.entity.AttendanceManagement;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.member.entity.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceManagementRepository extends JpaRepository<AttendanceManagement, Long> {
    List<AttendanceManagement> findAllByJobPost(
        JobPost jobPost
    );
    Optional<AttendanceManagement> findByMemberAndJobPost(
        Member member,
        JobPost jobPost
    );
}
