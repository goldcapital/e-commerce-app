package com.example.ecommerce.security;

import com.example.ecommerce.config.OauthProperties;
import com.example.ecommerce.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OauthProperties properties;
    public static final String[]AUTH_WHITELIST={
            "/api/v1/customer/auth/loge",
            "/api/v1/customer/create"

    };

    /*   @Bean
       public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
           security
                   .csrf(ServerHttpSecurity.CsrfSpec::disable)
                   .authorizeExchange(exchange -> exchange
                           .pathMatchers("/eureka/**")
                           .permitAll()
                           .anyExchange()
                           .authenticated())
                   .oauth2ResourceServer(oAuth2 -> oAuth2.jwt(Customizer.withDefaults()));
           return security.build();
       }*/
    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth -> auth
                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .pathMatchers("/api/v1/customer/exits/**","/api/v1/customer/get-all").hasRole("ADMIN")
                        .anyExchange()
                        .authenticated())
                .oauth2ResourceServer(oauth ->
                        oauth.jwt((jwt -> jwt.jwtAuthenticationConverter(jwtConverter()))))
                .build();


    }

    public Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtConverter() {
        var converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt ->
                Flux.fromIterable(SecurityUtils.grantedAuthorities(jwt.getClaims())));
        return converter;
    }

    @Bean
    public JwtDecoder jwtDecoder() {

        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(properties.getIssuerUri());

        OAuth2TokenValidator<Jwt> audienceValidator = token -> {
            List<String> audience = token.getAudience();
            if (audience.stream().anyMatch(properties.getAllowedAudience()::contains)) {
                return OAuth2TokenValidatorResult.success();
            } else {
                return OAuth2TokenValidatorResult.failure(new OAuth2Error("invalid_token", "The required audience is missing", null));
            }
        };
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(properties.getIssuerUri());
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

        jwtDecoder.setJwtValidator(withAudience);

        return jwtDecoder;
    }
}
