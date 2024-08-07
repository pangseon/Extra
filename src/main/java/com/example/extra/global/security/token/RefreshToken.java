package com.example.extra.global.security.token;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@RedisHash(value = "refreshToken", timeToLive = 60 * 60L)
public class RefreshToken {

    /**
     * 구조
     * <p>
     * - key -- "refreshToken:{@Id로 저장되는 값}
     * <p>
     * - 예제 -- new RefreshToken(2, "blabla") => "refreshToken:2"
     */
    @Id
    private String id;
    private String refreshToken;

}
