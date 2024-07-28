package com.example.extra.global.security.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 3)
@Getter
@AllArgsConstructor
public class RefreshToken {

    @Id
    private Long id;
    private String refreshToken;
    @Indexed
    private String accessToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
