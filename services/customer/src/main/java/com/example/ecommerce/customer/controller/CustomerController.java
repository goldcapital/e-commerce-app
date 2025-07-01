package com.example.ecommerce.customer.controller;

import com.example.ecommerce.customer.dto.CustomerRequest;
import com.example.ecommerce.customer.dto.CustomerResponse;
import com.example.ecommerce.customer.response.ApiResponse;
import com.example.ecommerce.customer.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.domain.PageRequest.of;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.creatCustomer(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        log.info("update customer request email {}", request.email());
        // service.updateCustomer(request);
        return ResponseEntity.ok(service.updateCustomer(request));
        // return ResponseEntity.accepted().build();
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<CustomerResponse>> findAll(@Min(value = 1, message = "number.min.value.1")
                                                          @RequestParam(defaultValue = "1") Integer page,
                                                          @Min(value = 10, message = "number.min.value.10")
                                                          @RequestParam(defaultValue = "10") Integer size
                                                        /*  @RequestHeader("X-User-Id") String userId,
                                                          @RequestHeader("X-User-Roles") String roles,
                                                          @RequestHeader("X-User-Username") String username*/) {

        /*log.warn("finALL customer ---{}, role {},username {}", userId, roles, username);*/
        // return ResponseEntity.ok(service.findAllCustomers(of(page-1,size, Sort.by("createdAt").descending())));
        return ResponseEntity.ok(service.findAllCustomers(of(page - 1, size)));
    }

    @GetMapping("/exits/{customer-id}")
    public ResponseEntity<Boolean> existsById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    @GetMapping("/get-by-id/{customer-id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.getById(customerId));
    }

    @DeleteMapping("/delete/{customer-id}")
    public ResponseEntity<Void> deleteById(@PathVariable("customer-id") String customerId) {
        service.deleteById(customerId);
        return ResponseEntity.accepted().build();
    }

}
