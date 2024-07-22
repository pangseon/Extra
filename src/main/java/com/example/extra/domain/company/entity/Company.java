package com.example.extra.domain.company.entity;

import com.example.extra.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_COMPANY")
@Entity
public class Company extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    private String companyUrl;

    // TODO - 회사-공고글 양방향 매핑할 지 확인 받기 + cascade 정책 확인 받기
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobPost> jobPostList = new ArrayList<>();

    // TODO - 코드 컨벤션(엔티티에 수정 로직 작성x) 지키면서 어떻게 양방향 처리 할 수 있을지 확인하기.
    // public void addJobPost(JobPost jobPost) {}

    @Builder
    public Company(
        String email,
        String password,
        String name,
        String companyUrl
    ){
        this.email = email;
        this.password = password;
        this.name = name;
        this.companyUrl = companyUrl;
    }
}