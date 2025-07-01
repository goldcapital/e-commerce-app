package com.example.ecommerce.order.controller;

import com.example.ecommerce.order.page.PageResponse;
import com.example.ecommerce.order.request.OrderRequest;
import com.example.ecommerce.order.response.OrderResponse;
import com.example.ecommerce.order.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        log.info("Create order request: {}", request);
        return ResponseEntity.ok(service.createdOrder(request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> findAll(
            @RequestParam(defaultValue = "1")
            @Min(value = 1, message = "Page number must be at least 1") Integer page,

            @RequestParam(defaultValue = "10")
            @Min(value = 10, message = "Page size must be at least 10")
            @Max(value = 50, message = "Page size must be at most 50") Integer size
    ) {
        // return ResponseEntity.ok(service.findAll(PageRequest.of(page - 1, size, Sort.by("caredAt").descending())));
        return ResponseEntity.ok(service.findAll(PageRequest.of(page - 1, size)));


    }

    @GetMapping("/{order-id}")
    private ResponseEntity<OrderResponse> finalById(@PathVariable("order-id") Integer orderId) {
        return ResponseEntity.ok(service.findById(orderId));
    }
}
