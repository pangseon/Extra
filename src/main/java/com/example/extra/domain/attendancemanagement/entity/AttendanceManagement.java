package com.example.extra.domain.attendancemanagement.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_ATTENDANCE_MANAGEMENT")
@Entity
public class AttendanceManagement extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime clockInTime;

    private LocalDateTime clockOutTime;

    @ColumnDefault("0")
    private Integer mealCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public AttendanceManagement(
        LocalDateTime clockInTime,
        LocalDateTime clockOutTime,
        Integer mealCount,
        JobPost jobPost,
        Member member
    ){
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.mealCount = mealCount;
        this.jobPost = jobPost;
        this.member = member;
    }

    public void updateClockInTimeTo(LocalDateTime clockInTime){
        this.clockInTime = clockInTime;
    }
    public void updateClockOutTimeTo(LocalDateTime clockOutTime){
        this.clockOutTime = clockOutTime;
    }
    public void addOneToMealCount(){
        this.mealCount += 1;
    }
}
