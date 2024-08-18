package com.example.extra.domain.role.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.domain.tattoo.entity.Tattoo;
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
import jakarta.validation.constraints.Size;
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

    @Column(nullable = false)
    @Size(max = 10)
    private String roleName;

    @Column(nullable = false)
    @Size(max = 50)
    private String costume;

    @Column(nullable = false)
    private Boolean sex;

    @Column(nullable = false)
    private LocalDate minAge;

    @Column(nullable = false)
    private LocalDate maxAge;

    @Column(columnDefinition = "SMALLINT UNSIGNED not null")
    private Integer limitPersonnel;

    @Column(columnDefinition = "SMALLINT UNSIGNED not null")
    private Integer currentPersonnel;

    @Column(nullable = false)
    private Season season;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "tattoo_id", nullable = false)
    private Tattoo tattoo;

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
        final Tattoo tattoo,
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
        this.tattoo = tattoo;
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
        Tattoo tattoo
    ) {
        this.roleName = roleName;
        this.costume = costume;
        this.sex = sex;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.limitPersonnel = limitPersonnel;
        this.currentPersonnel = currentPersonnel;
        this.season = season;
        this.tattoo = tattoo;
    }
    public void updateTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }
    public void addOneToCurrentPersonnel() {
        this.currentPersonnel += 1;
    }
    public void subtractOneToCurrentPersonnel() {
        this.currentPersonnel -= 1;
    }
}
