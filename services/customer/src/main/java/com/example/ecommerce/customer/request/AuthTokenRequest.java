package com.example.ecommerce.customer.request;

public record AuthTokenRequest(
        String name,
        String password
) {
}


