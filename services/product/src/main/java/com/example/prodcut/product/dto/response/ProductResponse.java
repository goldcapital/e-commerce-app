package com.example.prodcut.product.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        String color,

        double availableQuantity,
        BigDecimal price,
        Integer categoryId,

        String categoryName,
        String categoryDescription
) {
}
