package com.example.ecommerce.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.ecommerce.utils.Constants.*;

@UtilityClass
public class SecurityUtils {

    public static Collection<GrantedAuthority> grantedAuthorities(Map<String, Object> claims) {
        return extractRoles(claims).stream()
                .map(role -> new SimpleGrantedAuthority(ROLE + role))
                .collect(Collectors.toSet());
    }

    public static String extractUserName(Map<String, Object> claims) {

        Object realmAccessObj = claims.get("preferred_username");
        if (realmAccessObj instanceof String preferredUsername) {
            return preferredUsername;
        }
        return null;
    }

    public static List<String> extractRoles(Map<String, Object> claims) {
        Object realmAccessObj = claims.get(REALM_ACCESS);
        if (realmAccessObj instanceof Map<?, ?> accessMap) {
            Object rolesObj = accessMap.get(ROLES);
            if (rolesObj instanceof List<?> roleList) {
                return roleList.stream()
                        .map(String::valueOf)
                        .collect(Collectors.toList());
            }
        }
        return List.of();
    }
}

