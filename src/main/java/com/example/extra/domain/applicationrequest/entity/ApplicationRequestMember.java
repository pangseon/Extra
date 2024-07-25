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
@Table(name = "TB_APPLICATION_REQUEST_MEMBER",
    indexes = {
        @Index(columnList = "createdAt")
    }
)
@Entity
public class ApplicationRequestMember extends ApplicationRequest{
    @Builder
    public ApplicationRequestMember(
        ApplyStatus applyStatus,
        Member member,
        Role role
    ){
        this.applyStatus = applyStatus;
        this.member = member;
        this.role = role;
    }
}