package com.example.redis.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("spring-boot")
                .packagesToScan("com.example.redis") // 'com.example.demo'는 프로젝트의 패키지 이름으로 변경
                .build();
    }
}
