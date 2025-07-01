package com.example.ecommerce.order.config.kafka;

import com.example.ecommerce.order.customer.CustomerResponse;
import com.example.ecommerce.order.enums.PaymentMethod;
import com.example.ecommerce.product.response.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customerResponse,
        List<PurchaseResponse> products

) {
}
