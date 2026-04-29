package com.dev.ssc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        // 차후 타임아웃, 커넥션 풀, 헤더 등 공통 설정 포함할 것.
        return WebClient.builder();
    }
}
