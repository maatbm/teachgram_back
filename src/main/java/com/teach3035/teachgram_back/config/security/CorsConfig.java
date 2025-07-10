package com.teach3035.teachgram_back.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Value("${security.cors.routes}")
    private String routes;
    @Value("${security.cors.allowed-methods}")
    private String allowedMethods;
    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;
    @Value("${security.cors.allowed-headers}")
    private String allowedheaders;

    @Bean
    public WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping(routes)
                        .allowedMethods(allowedMethods)
                        .allowedOrigins(allowedOrigins)
                        .allowedHeaders(allowedheaders);
            }
        };
    }
}
