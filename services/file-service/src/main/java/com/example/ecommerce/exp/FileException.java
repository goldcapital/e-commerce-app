package com.example.ecommerce.exp;

import com.example.ecommerce.enums.Errors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileException extends RuntimeException {
    private final Errors errors;


}
