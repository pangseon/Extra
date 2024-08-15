package com.example.extra.global.security.oauth.dto.service.response;

import lombok.Builder;

@Builder
public record KakaoTokenInfoServiceResponseDto(
    String tokenType,
    String accessToken,
    Integer expiresIn,
    String refreshToken,
    Integer refreshTokenExpiresIn
) {

}
