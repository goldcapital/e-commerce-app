package com.example.ecommerce.service;

import com.example.ecommerce.dto.response.FileManagementDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import uz.isd.commons.result.CommonResultData;

import java.util.UUID;

public interface FileService {
    CommonResultData<FileManagementDTO> uploadFile(MultipartFile file, String bucketName);

    CommonResultData<FileManagementDTO> downloadFile(UUID fileId, HttpServletResponse servletResponse);

    void deleteFile(UUID id);

}
