package com.example.ecommerce.kafka.order;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record Product(
        @JsonProperty("productId") Integer productId,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("price") BigDecimal price,
        @JsonProperty("quantity") double quantity
) {
}
