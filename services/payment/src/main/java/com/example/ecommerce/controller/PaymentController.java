package com.example.ecommerce.controller;

import com.example.ecommerce.request.PaymentRequest;
import com.example.ecommerce.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/create")
    public ResponseEntity<Integer>createPayment(@RequestBody @Valid PaymentRequest request){
        return ResponseEntity.ok(paymentService.createPayment(request));

    }
}
