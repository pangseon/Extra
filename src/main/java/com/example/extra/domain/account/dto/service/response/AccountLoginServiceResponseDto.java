package com.example.extra.domain.account.dto.service.response;

public record AccountLoginServiceResponseDto(
    boolean isLogin,
    Long accountId,
    String accessToken,
    String refreshToken
) {

}
