package com.example.extra.global.security.oauth.dto.service.response;

import com.example.extra.global.security.oauth.entity.KakaoInfo;

public record KakaoLoginCheckServiceResponseDto(
    KakaoInfo kakaoInfo,
    boolean isSignup
) {

}
