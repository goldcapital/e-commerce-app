package com.example.ecommerce.repository;

import com.example.ecommerce.domain.FileMetadata;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileManagementRepository extends JpaRepository<FileMetadata, UUID> {
}
