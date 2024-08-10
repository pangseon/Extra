package com.example.extra.global.security.oauth.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoInfo {

    private String email;

    // 필요 정보 추후 추가

    public KakaoInfo(final String email) {
        this.email = email;
    }
}
