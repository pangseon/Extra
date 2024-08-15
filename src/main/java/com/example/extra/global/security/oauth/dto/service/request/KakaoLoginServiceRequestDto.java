package com.example.extra.global.security.oauth.dto.service.request;

import com.example.extra.global.enums.UserRole;
import lombok.Builder;

@Builder
public record KakaoLoginServiceRequestDto(
    String email,
    UserRole userRole
) {

}
