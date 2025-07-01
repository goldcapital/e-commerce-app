package com.example.ecommerce.payment;

import com.example.ecommerce.order.customer.CustomerResponse;
import com.example.ecommerce.order.enums.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
