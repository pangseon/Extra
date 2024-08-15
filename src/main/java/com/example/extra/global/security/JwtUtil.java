package com.example.extra.global.security;

import com.example.extra.domain.account.entity.Account;
import com.example.extra.domain.account.repository.AccountRepository;
import com.example.extra.global.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "JWT 로그")
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 유효 기간 (밀리초로 계산 / 1주일 = 604800초)
    private static final long TOKEN_VALIDITY_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_VALIDITY_TIME = 60 * 60 * 3 * 1000L;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;

    // repository
    private final AccountRepository accountRepository;

    @PostConstruct
    protected void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String getTokenFromRequest(HttpServletRequest httpServletRequest)
        throws IOException, ServletException {
        return httpServletRequest.getHeader(AUTHORIZATION_HEADER);
    }

    public String createToken(String username, UserRole role) {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setSubject(username)
                .claim(AUTHORIZATION_KEY, role)
                .setExpiration(new Date(date.getTime() + TOKEN_VALIDITY_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();

        return BEARER_PREFIX +
            Jwts.builder()
                .setClaims(Jwts.claims())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                    new Date(date.getTime() + REFRESH_TOKEN_VALIDITY_TIME))
                .signWith(key, signatureAlgorithm)
                .compact();
    }

//    public void addJwtCookie(String token, HttpServletResponse res) {
//        try {
//            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
//
//            Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
//            cookie.setPath("/");
//
//            res.addCookie(cookie);
//        } catch (UnsupportedEncodingException e) {
//            log.error(e.getMessage());
//        }
//    }

    public void addTokenHeader(String token, HttpServletResponse response) {
        response.addHeader(AUTHORIZATION_HEADER, token);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        } else {
            throw new NullPointerException("Not Found Token");
        }
    }

    public Boolean isExpired(String token) {
        long expireTime = Jwts
            .parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getExpiration()
            .getTime();
        long now = new Date().getTime();

        return !(((expireTime - now) % 1000) + 1 >= 0);
    }

    public boolean validateToken(String token) {
        try {
            Claims info = getUserInfoFromToken(token);
            log.info(info.toString());
            Account account = accountRepository.findByEmail(info.getSubject()).orElse(null);

            if (account != null && account.getRefreshToken() != null) {
                // access token 유효/만료 확인
                return !isExpired(token);
            }
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature : 잘못된 JWT 서명");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token : 만료된 JWT token");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported Jwt token : 지원하지 않는 JWT token");
        } catch (IllegalArgumentException e) {
            log.error("Jwt claims is empty : 잘못된 JWT token");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
