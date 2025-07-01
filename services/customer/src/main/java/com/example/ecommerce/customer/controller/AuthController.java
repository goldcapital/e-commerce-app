package com.example.ecommerce.customer.controller;

import com.example.ecommerce.customer.request.AuthTokenRequest;
import com.example.ecommerce.customer.response.AuthTokenResponse;
import com.example.ecommerce.customer.service.KeycloakService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.isd.commons.result.CommonResultData;

@RestController
@RequestMapping("/api/v1/customer/auth")
@RequiredArgsConstructor
public class AuthController {
    private final KeycloakService service;

    @PostMapping("/loge")
    public ResponseEntity<CommonResultData<AuthTokenResponse>> generateToken(@Valid @RequestBody AuthTokenRequest request) {
        return ResponseEntity.ok(service.getToken(request));

    }
}
