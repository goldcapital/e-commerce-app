package com.example.ecommerce.customer.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserContextFilter extends OncePerRequestFilter {
    private final UserContext userContext;

    public UserContextFilter(UserContext userContext) {
        this.userContext = userContext;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        userContext.setCustomerId(request.getHeader("X-User-Id"));
        userContext.setUsername(request.getHeader("X-User-Username"));

        var roles = request.getHeader("X-User-Roles");
        if (roles != null) {
            userContext.setRoles(Arrays.asList(roles.split(",")));
        }

        filterChain.doFilter(request, response);
    }
}
