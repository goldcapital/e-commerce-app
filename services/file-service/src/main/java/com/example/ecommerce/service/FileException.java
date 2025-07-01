package com.example.ecommerce.service;

import com.example.ecommerce.Errors;

public class FileException extends RuntimeException {
    private final Errors errors;

    public FileException(Errors p0) {
        super(p0.getMessage());
        this.errors = p0;
    }
}
