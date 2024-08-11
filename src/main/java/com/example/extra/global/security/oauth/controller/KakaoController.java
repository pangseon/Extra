package com.example.extra.global.security.oauth.controller;

import com.example.extra.global.security.oauth.dto.service.request.KakaoLoginServiceRequestDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoLoginServiceResponseDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoTokenInfoServiceResponseDto;
import com.example.extra.global.security.oauth.entity.KakaoInfo;
import com.example.extra.global.security.oauth.service.KakaoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> kakaoLogin(@RequestParam String code)
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
        try {
            kakaoInfo = oauthKakaoService.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // 현재 kakao에서 email 정보 가져오기 불가능
        // 고유 회원번호로 email 대체
        String email = kakaoInfo.getId().toString();
        KakaoLoginServiceRequestDto loginServiceRequestDto
            = new KakaoLoginServiceRequestDto(email);

        KakaoLoginServiceResponseDto loginServiceResponseDto =
            oauthKakaoService.signup(loginServiceRequestDto);

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(
                AUTHORIZATION_HEADER,
                loginServiceResponseDto.accessToken()
            )
            .body(tokenInfoServiceResponseDto);
    }
}
