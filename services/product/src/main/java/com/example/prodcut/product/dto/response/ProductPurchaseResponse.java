package com.example.prodcut.product.dto.response;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer productId,
        String name,
        String description,
        String productColor,
        BigDecimal price,
        double quantity
) {
}
