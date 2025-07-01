package com.example.ecommerce.customer.config;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
@Data
@Component
@RequestScope
public class UserContext {
    private String customerId;
    private String username;
    private List<String> roles;
}
