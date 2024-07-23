package com.example.extra.domain.attendancemanagement.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_ATTENDANCE_MANAGEMENT")
@Entity
public class AttendanceManagement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clock_in_time")
    private LocalDateTime clockInTime;

    @Column(name = "clock_out_time")
    private LocalDateTime clockOutTime;

    @Column(columnDefinition = "boolean default false")
    private Boolean breakfast;

    @Column(columnDefinition = "boolean default false")
    private Boolean lunch;

    @Column(columnDefinition = "boolean default false")
    private Boolean dinner;

    @OneToOne(optional = false)
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public AttendanceManagement(
        LocalDateTime clockInTime,
        LocalDateTime clockOutTime,
        Boolean breakfast,
        Boolean lunch,
        Boolean dinner,
        JobPost jobPost,
        Member member
    ){
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.jobPost = jobPost;
        this.member = member;
    }
}
