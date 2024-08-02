package com.example.extra.global.security.controller;

import com.example.extra.global.security.UserDetailsImpl;
import com.example.extra.global.security.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/token")
    public ResponseEntity<?> getRefreshToken(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) {
        refreshTokenService.getNewAccessToken(
            userDetails,
            httpServletRequest,
            httpServletResponse
        );
        return ResponseEntity.ok().build();
    }
}
