package com.example.extra.domain.tattoo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TATTOO")
@Entity
public class Tattoo {

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

    @Builder
    public Tattoo(
        Boolean face,
        Boolean chest,
        Boolean arm,
        Boolean leg,
        Boolean shoulder,
        Boolean back,
        Boolean hand,
        Boolean feet
    ) {
        this.face = face;
        this.chest = chest;
        this.arm = arm;
        this.leg = leg;
        this.shoulder = shoulder;
        this.back = back;
        this.hand = hand;
        this.feet = feet;
    }
}
