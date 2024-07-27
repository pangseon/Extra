package com.example.extra.global.filter;

import com.example.extra.global.security.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 인증 & 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getRequestURI().equals("/api/v1/members/signup")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtUtil.getJwtTokenFromRequest(request);
        log.info("추출된 토큰: {}", token);

        if (StringUtils.hasText(token)) {
            token = jwtUtil.substringToken(token);
            log.info("Token after Bearer removal: {}", token);

            if (!jwtUtil.validateToken(token)) {
                log.error("Token validation failed");
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(token);
            log.info("Claims from token: {}", info);

            try {
                setAuthentication(info.getSubject());
                log.info("Authentication set for user: {}", info.getSubject());
            } catch (Exception e) {
                log.error("Error processing token: {}", e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        log.info("Setting authentication for user: {}", username);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String username) {
        log.info("Loading user details for username: {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        log.info("User details loaded: {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities()
        );
    }
}
