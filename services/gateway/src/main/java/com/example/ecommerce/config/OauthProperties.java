package com.example.ecommerce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Data
@Configuration
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
public class OauthProperties {
    private String issuerUri;
    private Set<String> allowedAudience = new HashSet<>();
}
