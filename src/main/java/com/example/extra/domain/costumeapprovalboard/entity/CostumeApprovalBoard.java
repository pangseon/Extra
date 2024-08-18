package com.example.extra.domain.costumeapprovalboard.entity;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.role.entity.Role;
import com.example.extra.global.entity.BaseEntity;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(nullable = false) // varchar(255)
    private ApplyStatus costumeApprove;

    @Column(nullable = false) // varchar(255)
    private String costumeImageUrl;

    @Column(nullable = false) // varchar(255)
    private String folderName;

    @Column(nullable = false) // varchar(255)
    private String imageExplain;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Builder
    public CostumeApprovalBoard(
        final String costumeImageUrl,
        final String imageExplain,
        final Member member,
        final Role role,
        final String folderName
    ) {
        this.costumeApprove = ApplyStatus.APPLIED;
        this.costumeImageUrl = costumeImageUrl;
        this.imageExplain = imageExplain;
        this.member = member;
        this.role = role;
        this.folderName = folderName;
    }

    public void updateImageExplain(String imageExplain) {
        this.imageExplain = imageExplain;
    }

    public void updateCostumeImageUrl(String costumeImageUrl) {
        this.costumeImageUrl = costumeImageUrl;
    }

    public void updateCostumeApprove(ApplyStatus applyStatus) {
        this.costumeApprove = applyStatus;
    }
}
