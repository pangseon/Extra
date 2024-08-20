package com.example.extra.domain.account.dto.service.response;

public record AccountLoginServiceResponseDto(
    boolean isLoginPossible,
    Long accountId,
    String accessToken,
    String refreshToken
) {

}
