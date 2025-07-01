package com.example.ecommerce.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.ecommerce.security.SecurityConfig.AUTH_WHITELIST;
import static com.example.ecommerce.utils.SecurityUtils.extractRoles;
import static com.example.ecommerce.utils.SecurityUtils.extractUserName;

@Configuration
public class JwtEnrichmentFilter implements GlobalFilter {
    private final List<PathPattern> whitelistPatterns;

    public JwtEnrichmentFilter() {
        PathPatternParser parser = new PathPatternParser();
        this.whitelistPatterns = Arrays.stream(AUTH_WHITELIST)
                .map(parser::parse)
                .toList();
    }
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }
        return ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    Authentication auth = securityContext.getAuthentication();
                    if (auth instanceof JwtAuthenticationToken jwtToken) {
                        Map<String, Object> claims = jwtToken.getToken().getClaims();

                        String userId = claims.get("sub").toString();
                        List<String> roles = extractRoles(claims);
                        String username=extractUserName(claims);

                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-Id", userId)
                                .header("X-User-Roles", String.join(",", roles))
                                .header("X-User-Username",username)
                                .build();

                        return exchange.mutate().request(mutatedRequest).build();
                    }
                    return exchange;
                })
                .flatMap(chain::filter);
    }
    private boolean isWhitelisted(String path) {
        return whitelistPatterns.stream()
                .anyMatch(pattern -> pattern.matches(PathContainer.parsePath(path)));
    }
}

