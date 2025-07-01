package com.example.ecommerce.order.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product is manda")
        Integer productId,
        @Positive(message = "Quantity is ")
        double quantity
) {
}
