package com.example.ecommerce.kafka.order;

import com.example.ecommerce.notification.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        @JsonProperty("customerResponse")
        Customer customer,
        List<Product>products
) {
}
