package com.example.ecommerce.customer.dto;

import com.example.ecommerce.customer.entity.Address;

public record CustomerResponse(String id,
                               String firstname,
                               String lastname,
                               String email,
                               Address address) {
}
