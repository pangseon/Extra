package com.example.extra.domain.refreshtoken.controller;

import com.example.extra.domain.refreshtoken.dto.RefreshTokenCreateServiceResponseDto;
import com.example.extra.domain.refreshtoken.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @GetMapping("/token")
    public ResponseEntity<?> getNewAccessToken(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    ) {
        RefreshTokenCreateServiceResponseDto serviceResponseDto =
            refreshTokenService.getNewAccessToken(
                httpServletRequest,
                httpServletResponse
            );

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(serviceResponseDto.refreshToken());
    }
}
