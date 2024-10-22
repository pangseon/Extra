package com.example.extra.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;
    //    @Value("${spring.data.redis.password}")
    private String redisPassword;

    @Bean // 레디스 환경 설정 (host, port, password)
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration =
            new RedisStandaloneConfiguration(host, port);
//        configuration.setPassword(redisPassword);
        return new LettuceConnectionFactory(configuration);
    }
}
