package com.example.extra.domain.role.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.schedule.entity.Schedule;
import com.example.extra.global.entity.BaseEntity;
import com.example.extra.global.enums.Season;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_ROLE")
@Entity
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roleName;

    @Column
    private String costume;

    @Column
    private Boolean sex;

    @Column
    private LocalDate roleAge;

    @Column
    private Integer limitPersonnel;

    @Column
    private Integer currentPersonnel;

    @Column
    private Season season;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_post_id",nullable = false)
    private JobPost jobPost;
    @Builder
    public Role(
        String  roleName,
        String costume,
        Boolean sex,
        LocalDate roleAge,
        Integer limitPersonnel,
        Integer currentPersonnel,
        Season season,
        JobPost jobPost
    ){
        this.roleName = roleName;
        this.costume = costume;
        this.sex = sex;
        this.roleAge = roleAge;
        this.limitPersonnel = limitPersonnel;
        this.currentPersonnel = currentPersonnel;
        this.season = season;
        this.jobPost = jobPost;
    }

}
