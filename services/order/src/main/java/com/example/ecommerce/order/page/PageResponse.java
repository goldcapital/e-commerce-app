package com.example.ecommerce.order.page;

import com.example.ecommerce.order.response.OrderResponse;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int size,
        int page) {
}

