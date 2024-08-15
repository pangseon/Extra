package com.example.extra.global.security.oauth.controller;

import com.example.extra.global.security.oauth.dto.controller.KakaoLoginControllerRequestDto;
import com.example.extra.global.security.oauth.dto.service.request.KakaoLoginServiceRequestDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoLoginCheckServiceResponseDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoTokenInfoServiceResponseDto;
import com.example.extra.global.security.oauth.entity.KakaoInfo;
import com.example.extra.global.security.oauth.service.KakaoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/oauth")
public class KakaoController {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final KakaoServiceImpl oauthKakaoService;

    // 인가 코드 받기
    @GetMapping("/authorize")
    public String authorize(
    ) {
        return oauthKakaoService.authorize();
    }

    // access token & refresh token 발급, kakao email 발급
    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(
        @RequestParam String code,
        @RequestBody KakaoLoginControllerRequestDto controllerRequestDto
    )
        throws JsonProcessingException {
        KakaoTokenInfoServiceResponseDto tokenInfoServiceResponseDto;
        try {
            // [0] accessToken, [1] refreshToken
            tokenInfoServiceResponseDto = oauthKakaoService.getToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String accessToken = tokenInfoServiceResponseDto.accessToken();

        KakaoInfo kakaoInfo = null;
        boolean isSignup = false;
        try {
            KakaoLoginCheckServiceResponseDto loginCheckServiceResponseDto =
                oauthKakaoService.getKakaoInfo(accessToken);
            kakaoInfo = loginCheckServiceResponseDto.kakaoInfo();
            isSignup = loginCheckServiceResponseDto.isSignup();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 현재 kakao에서 email 정보 가져오기 불가능
        // 고유 회원번호로 email 대체
        String email = kakaoInfo.getId().toString();
        KakaoLoginServiceRequestDto loginServiceRequestDto
            = KakaoLoginServiceRequestDto.builder()
            .email(email)
            .userRole(controllerRequestDto.userRole())
            .build();

        // 회원 가입을 한 경우 -> login
        // 회원 가입을 하지 않은 경우 -> signup
        if (isSignup) {
            return ResponseEntity
                .status(HttpStatus.OK)
                .header(AUTHORIZATION_HEADER,
                    oauthKakaoService.login(loginServiceRequestDto).accessToken())
                .build();
        } else {
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    oauthKakaoService.signup(loginServiceRequestDto)
                );
        }
    }
}
