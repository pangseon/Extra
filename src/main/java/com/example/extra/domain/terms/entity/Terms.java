package com.example.extra.domain.terms.entity;

import com.example.extra.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TB_TERMS")
@Entity
public class Terms extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private Boolean optional;

    // 양방향 매핑 필요 없어 보여서 반영 안함
    @Builder
    public Terms(
        String title,
        String content,
        Boolean optional
    ){
        this.title = title;
        this.content = content;
        this.optional = optional;
    }
}
