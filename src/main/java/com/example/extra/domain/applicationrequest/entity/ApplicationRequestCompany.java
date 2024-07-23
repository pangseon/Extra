package com.example.extra.domain.applicationrequest.entity;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.common.BaseEntity;
import com.example.extra.global.enums.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_APPLICATION_REQUEST_COMPANY",
    indexes = {
        @Index(columnList = "createdAt")
    }
)
@Entity
public class ApplicationRequestCompany extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_status", nullable = false)
    private ApplyStatus applyStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    public ApplicationRequestCompany(
        ApplyStatus applyStatus,
        Member member,
        Role role
    ){
        this.applyStatus = applyStatus;
        this.member = member;
        this.role = role;
    }
}
