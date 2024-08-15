package com.example.extra.global.security.oauth.dto.controller;

import com.example.extra.global.enums.UserRole;

public record KakaoLoginControllerRequestDto(
    UserRole userRole
) {

}
