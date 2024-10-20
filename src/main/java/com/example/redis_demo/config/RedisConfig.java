package com.example.redis_demo.config;

import com.example.redis_demo.converter.BitesToCustomKeyConverter;
import com.example.redis_demo.converter.CustomKeyToBitesConverter;
import com.example.redis_demo.converter.CustomKeyToStringConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    private final CustomKeyToBitesConverter customKeyToBitesConverter;
    private final CustomKeyToStringConverter customKeyToStringConverter;
    private final BitesToCustomKeyConverter bitesToCustomKeyConverter;

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(List.of(customKeyToBitesConverter,
                customKeyToStringConverter,
                bitesToCustomKeyConverter));
    }
}