package com.example.ecommerce.exp;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
