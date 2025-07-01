package com.example.ecommerce.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private  boolean success;
    private String message;
    private  T data;


    public static <T> ApiResponse<T> of(boolean success, String message, T data) {
        return new ApiResponse(success, message, data);
    }
}
