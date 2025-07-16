package com.teach3035.teachgram_back.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("POST", "GET", "PATCH", "DELETE")
                        .allowedOrigins(allowedOrigins)
                        .allowedHeaders("Content-Type", "Authorization");
            }
        };
    }
}
