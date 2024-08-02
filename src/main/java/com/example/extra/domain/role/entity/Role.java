package com.example.extra.domain.role.entity;

import com.example.extra.domain.jobpost.entity.JobPost;
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
    private String role_name;

    @Column
    private String costume;

    @Column
    private Boolean sex;

    @Column
    private LocalDate role_age;

    @Column
    private Integer limit_personnel;

    @Column
    private Integer current_personnel;

    @Column
    private Season season;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "job_post_id",nullable = false)
    private JobPost jobPost;
    @Builder
    public Role(
        String  role_name,
        String costume,
        Boolean sex,
        LocalDate role_age,
        Integer limit_personnel,
        Integer current_personnel,
        Season season,
        JobPost jobPost
    ){
        this.role_name = role_name;
        this.costume = costume;
        this.sex = sex;
        this.role_age = role_age;
        this.limit_personnel = limit_personnel;
        this.current_personnel = current_personnel;
        this.season = season;
        this.jobPost = jobPost;
    }

    public void addOneToCurrentPersonnel(){
        this.currentPersonnel += 1;
    }
    public void subtractOneToCurrentPersonnel(){
        this.currentPersonnel -= 1;
    }
}
