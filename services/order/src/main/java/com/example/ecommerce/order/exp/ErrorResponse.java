package com.example.ecommerce.order.exp;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> stringMap
) {

}
