package com.example.DigiMath_backend.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for managing
 * frontend-related properties
 */
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "frontend.url")
public class FrontendConfig {
    private String baseUrl;
    private String loginUrl;
    @Value(value = "${frontend.url}")
    private String forgottenPasswordUrl;
    private String oauth2RedirectUrl;
}

