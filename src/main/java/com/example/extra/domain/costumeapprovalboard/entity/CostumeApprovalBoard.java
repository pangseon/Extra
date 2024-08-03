package com.example.extra.domain.costumeapprovalboard.entity;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.entity.BaseEntity;
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
@Table(name = "TB_COSTUME_APPROVAL_BOARD",
    indexes = {
    @Index(columnList = "createdAt"),
    @Index(columnList = "modifiedAt")
}
)
@Entity
public class CostumeApprovalBoard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "costume_approve", columnDefinition = "boolean default false")
    private Boolean costumeApprove;

    // TODO - DTO에 @NotBlank constraint 붙이기.
    @Column(name = "costume_image_url", nullable = false)
    private String costumeImageUrl;

    @Column
    private String image_explain;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder
    public CostumeApprovalBoard(
        Boolean costumeApprove,
        String costumeImageUrl,
        Member member,
        Role role
    ){
        this.costumeApprove = costumeApprove;
        this.costumeImageUrl = costumeImageUrl;
        this.member = member;
        this.role = role;
    }
}
