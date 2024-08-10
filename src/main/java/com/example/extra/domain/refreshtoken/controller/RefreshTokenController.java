package com.example.extra.domain.refreshtoken.controller;

import com.example.extra.domain.refreshtoken.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/member/token")
    public ResponseEntity<?> getMemberRefreshToken(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) {
        refreshTokenService.getMemberNewAccessToken(
            httpServletRequest,
            httpServletResponse
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/company/token")
    public ResponseEntity<?> getCompanyRefreshToken(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) {
        refreshTokenService.getCompanyNewAccessToken(
            httpServletRequest,
            httpServletResponse
        );
        return ResponseEntity.ok().build();
    }
}
