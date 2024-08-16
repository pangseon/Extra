package com.example.extra.domain.role.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.global.entity.BaseEntity;
import com.example.extra.global.enums.Season;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    private LocalDate minAge;

    @Column
    private LocalDate maxAge;

    @Column
    private Integer limitPersonnel;

    @Column
    private Integer currentPersonnel;

    @Column
    private Season season;

    @Column
    private Boolean checkTattoo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_post_id", nullable = false)
    private JobPost jobPost;

    @Builder
    public Role(
        final String roleName,
        final String costume,
        final Boolean sex,
        final LocalDate minAge,
        final LocalDate maxAge,
        final Integer limitPersonnel,
        final Integer currentPersonnel,
        final Season season,
        final Boolean checkTattoo,
        final JobPost jobPost
    ) {
        this.roleName = roleName;
        this.costume = costume;
        this.sex = sex;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.limitPersonnel = limitPersonnel;
        this.currentPersonnel = currentPersonnel;
        this.season = season;
        this.checkTattoo = checkTattoo;
        this.jobPost = jobPost;
    }

    public void updateRole(
        String roleName,
        String costume,
        Boolean sex,
        LocalDate minAge,
        LocalDate maxAge,
        Integer limitPersonnel,
        Integer currentPersonnel,
        Season season,
        Boolean checkTattoo
    ) {
        this.roleName = roleName;
        this.costume = costume;
        this.sex = sex;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.limitPersonnel = limitPersonnel;
        this.currentPersonnel = currentPersonnel;
        this.season = season;
        this.checkTattoo = checkTattoo;
    }

    public void addOneToCurrentPersonnel() {
        this.currentPersonnel += 1;
    }
    public void subtractOneToCurrentPersonnel() {
        this.currentPersonnel -= 1;
    }
}
