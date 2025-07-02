package com.example.prodcut;

import java.util.UUID;

public record FileManagementDTO(
        UUID id,
        String fileName,

        String filePath,

        String extension,

        String bucketName
) {
}
