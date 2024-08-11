package com.example.extra.global.security.oauth.service;

import com.example.extra.domain.company.exception.CompanyErrorCode;
import com.example.extra.domain.company.exception.CompanyException;
import com.example.extra.domain.company.repository.CompanyRepository;
import com.example.extra.domain.member.entity.Member;
import com.example.extra.domain.member.exception.MemberErrorCode;
import com.example.extra.domain.member.exception.MemberException;
import com.example.extra.domain.member.repository.MemberRepository;
import com.example.extra.domain.refreshtoken.repository.RefreshTokenRepository;
import com.example.extra.domain.refreshtoken.token.RefreshToken;
import com.example.extra.global.security.JwtUtil;
import com.example.extra.global.security.oauth.dto.service.request.KakaoLoginServiceRequestDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoLoginServiceResponseDto;
import com.example.extra.global.security.oauth.dto.service.response.KakaoTokenInfoServiceResponseDto;
import com.example.extra.global.security.oauth.entity.KakaoInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoServiceImpl {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    @Value("${kakao.client.id}")
    String clientId;
    @Value("${kakao.redirect.uri}")
    String redirectUri;

    public String authorize() {
        String url = "https://kauth.kakao.com/oauth/authorize" +
            "&client_id=" + clientId +
            "&redirect_uri=" + redirectUri +
            "&response_type=code";

        return "redirect:" + url;
    }

    public KakaoTokenInfoServiceResponseDto getToken(String code) throws JsonProcessingException {
        // HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(
            "Content-type",
            "application/x-www-form-urlencoded;charset=utf-8"
        );

        // HTTP Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);

        // HTTP request
        HttpEntity<MultiValueMap<String, String>> request =
            new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
            restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                request,
                String.class
            );

        // response - json parsing
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return KakaoTokenInfoServiceResponseDto.builder()
            .tokenType("Bearer")
            .accessToken(jsonNode.get("access_token").asText())
            .expiresIn(jsonNode.get("expires_in").asInt())
            .refreshToken(jsonNode.get("refresh_token").asText())
            .refreshTokenExpiresIn(jsonNode.get("refresh_token_expires_in").asInt())
            .build();
    }

    // 중복 확인
    private void validate(final String email) {
        memberRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new MemberException(MemberErrorCode.ALREADY_EXIST_MEMBER);
            });
        companyRepository.findByEmail(email)
            .ifPresent(m -> {
                throw new CompanyException(CompanyErrorCode.ALREADY_EXIST_EMAIL);
            });
    }

    public KakaoInfo getKakaoInfo(String accessToken)
        throws JsonProcessingException {
        // HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(
            "Authorization",
            "Bearer " + accessToken
        );
        headers.add(
            "Content-type",
            "application/x-www-form-urlencoded;charset=utf-8"
        );

        // HTTP request
        HttpEntity<MultiValueMap<String, String>> request =
            new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
            restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class
            );

        // response - json parsing
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode
            .get("id")
            .asLong();
        String email = jsonNode
            .get("kakao_account")
            .get("email")
            .asText();

        KakaoInfo kakaoInfo = new KakaoInfo(id, email);
        validate(kakaoInfo.getId().toString());

        return kakaoInfo;
    }

    @Transactional
    public KakaoLoginServiceResponseDto signup(
        final KakaoLoginServiceRequestDto serviceRequestDto
    ) {
        String email = serviceRequestDto.email();
        validate(email);

        String uuid = UUID.randomUUID()
            .toString()
            .replace("-", "");

        Member member = Member.builder()
            .email(email)
            .password(uuid)
            .build();

        // jwt 토큰 생성
        String accessToken = jwtUtil.createToken(
            member.getEmail(),
            member.getUserRole()
        );
        String refreshToken = jwtUtil.createRefreshToken();
        log.info("access token: " + accessToken);
        log.info("refresh token: " + refreshToken);

        member.updateRefreshToken(jwtUtil.substringToken(refreshToken));

        refreshTokenRepository.save(
            new RefreshToken(
                member.tokenId(),
                jwtUtil.substringToken(refreshToken)
            )
        );

        return new KakaoLoginServiceResponseDto(accessToken);
    }
}
