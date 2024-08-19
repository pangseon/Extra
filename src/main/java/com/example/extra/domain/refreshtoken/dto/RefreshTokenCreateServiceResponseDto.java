package com.example.extra.domain.refreshtoken.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record RefreshTokenCreateServiceResponseDto(
    @Schema(description = "리프레시 토큰", example = "Bearer token")
    String refreshToken
) {

}
