package com.example.prodcut.product.exp;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {
}
