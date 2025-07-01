package com.example.ecommerce.customer.dto;

import com.example.ecommerce.customer.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        String id,
        @NotBlank(message = "Customer firstname is required")
        String firstname,
        @NotBlank(message = "Customer lastname is required")
        String lastname,
        @NotBlank(message = "Customer email is required")
        @Email(message = "Customer email is not valid email address")
        String email,
        @NotBlank
        String password,
        Address address

) {
}
