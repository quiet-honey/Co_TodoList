package com.example.Co_Todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해
        registry.addMapping("/**") // CORS를 적용할 URL 패턴을 정의 (여기서는 와일드카드를 사용)
                .allowedOrigins("http://localhost:3000") // Origin(http:localhost:3000)에 대해
                // GET, POST, PUT, PATCH, DELETE, OPTIONS 메서드를 허용한다.
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*") // 모든 헤더 허용
                // MAX_AGE_SECS만큼 pre-flight 리퀘스트(사전에 서버에서 어떤 origin과 어떤 method를 허용하는지 브라우저에게 알려주는 역할)를 캐싱
                .maxAge(MAX_AGE_SECS);
    }
}
