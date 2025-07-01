package com.example.ecommerce;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record FileManagementDTO(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,

        String fileName,

        String filePath,

        String extension,

        String bucketName
) {
}
