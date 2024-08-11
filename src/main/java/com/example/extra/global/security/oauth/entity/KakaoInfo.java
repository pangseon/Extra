package com.example.extra.global.security.oauth.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoInfo {

    private Long id;
    private String email;

    // 필요 정보 추후 추가

    public KakaoInfo(final Long id, final String email) {
        this.id = id;
        this.email = email;
    }
}
