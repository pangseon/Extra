package com.example.extra.domain.company.entity;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.jobpost.entity.JobPost;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    private String name;

    @Column
    private String companyUrl;

    @OneToOne(
        optional = false,
        fetch = FetchType.LAZY
    )
    private Account account;

    // TODO - 회사-공고글 양방향 매핑할 지 확인 받기 + cascade 정책 확인 받기
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<JobPost> jobPostList = new ArrayList<>();

    // TODO - 코드 컨벤션(엔티티에 수정 로직 작성x) 지키면서 어떻게 양방향 처리 할 수 있을지 확인하기.
    // public void addJobPost(JobPost jobPost) {}

    @Builder
    public Company(
        final String name,
        final String companyUrl,
        final Account account
    ) {
        this.name = name;
        this.companyUrl = companyUrl;
        this.account = account;
    }

    public String tokenId() {
        return this.id.toString() + "C";
    }

}