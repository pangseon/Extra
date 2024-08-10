package com.example.extra.global.security.oauth.controller;

import com.example.extra.global.security.oauth.dto.service.response.KakaoInfoReadServiceResponseDto;
import com.example.extra.global.security.oauth.service.KakaoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
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
    public ResponseEntity<?> getAccessToken(@RequestParam String code)
        throws JsonProcessingException {

        List<String> tokenList;
        try {
            // [0] accessToken, [1] refreshToken
            tokenList = oauthKakaoService.getToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String accessToken = tokenList.get(0);

        KakaoInfoReadServiceResponseDto responseDto = null;
        try {
            responseDto = oauthKakaoService.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity
            .status(HttpStatus.OK)
            .header(
                AUTHORIZATION_HEADER,
                accessToken
            )
            .body(responseDto);
    }
}
