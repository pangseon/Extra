package com.example.extra.domain.tattoo.entity;

import com.example.extra.domain.member.entity.Member;
import com.example.extra.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TATTOO")
@Entity
@ToString
public class Tattoo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private Boolean face;

    @Column(columnDefinition = "boolean default false")
    private Boolean chest;

    @Column(columnDefinition = "boolean default false")
    private Boolean arm;

    @Column(columnDefinition = "boolean default false")
    private Boolean leg;

    @Column(columnDefinition = "boolean default false")
    private Boolean shoulder;

    @Column(columnDefinition = "boolean default false")
    private Boolean back;

    @Column(columnDefinition = "boolean default false")
    private Boolean hand;

    @Column(columnDefinition = "boolean default false")
    private Boolean feet;

    private String etc;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Tattoo(
        Boolean face,
        Boolean chest,
        Boolean arm,
        Boolean leg,
        Boolean shoulder,
        Boolean back,
        Boolean hand,
        Boolean feet,
        String etc,
        Member member
    ) {
        this.face = face;
        this.chest = chest;
        this.arm = arm;
        this.leg = leg;
        this.shoulder = shoulder;
        this.back = back;
        this.hand = hand;
        this.feet = feet;
        this.etc = etc;
        this.member = member;
    }
}