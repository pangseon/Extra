package com.example.extra.domain.applicationrequest.entity;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.enums.ApplyStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
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
public class ApplicationRequestCompany extends ApplicationRequest{
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
