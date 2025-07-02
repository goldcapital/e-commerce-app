package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.FileMetadata;
import com.example.ecommerce.dto.response.FileManagementDTO;
import com.example.ecommerce.mapper.FileManagementMapper;
import com.example.ecommerce.repository.FileManagementRepository;
import com.example.ecommerce.service.FileService;
import com.example.ecommerce.service.MinioService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.isd.commons.result.CommonResultData;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import static com.example.ecommerce.exp.ErrorMessage.*;
import static com.example.ecommerce.utile.FilesUtils.*;
import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final MinioService minioService;
    private final FileManagementRepository fileManagementRepository;
    private final FileManagementMapper fileManagementMapper;

    @Override
    public CommonResultData<FileManagementDTO> uploadFile(MultipartFile file, String bucketName) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(FILE_MUST_NOT_NULL);
        }

        if (bucketName == null || bucketName.isBlank()) {
            throw new IllegalArgumentException(BUCKET_NAME_MUST_NOT_NULL);
        }
        var fileName = getFileName(file.getOriginalFilename());
        var filePath = createPath(fileName);


        var genericResponse = minioService.uploadFile(file, bucketName, fileName);
        var saved = fileManagementRepository.save(fileManagementMapper.toEntity(filePath,
                fileName, file.getOriginalFilename(), genericResponse.bucket()));

        return new CommonResultData<>(fileManagementMapper.toDto(saved));
    }

    @Override
    public CommonResultData<FileManagementDTO> downloadFile(UUID fileId, HttpServletResponse servletResponse) {

        var fileManagement = getFileOrThrow(fileId);
        var file = minioService.downloadFile(fileManagement.getFilePath(), fileManagement.getBucketName());
        byte[] byteContent = toByteArray(file);
        servletResponse.setContentType("application/octet-stream");
        servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileManagement.getFileName() + "\"");

        try (OutputStream os = servletResponse.getOutputStream()) {
            os.write(byteContent);
            os.flush();
        } catch (IOException e) {
            throw new RuntimeException(format(THERE_WAS_AN_ERROR_UPLOADING_THE_FILE, fileId));
        }
        return null;
    }

    @Override
    public void deleteFile(UUID id) {
        var fileManagement = getFileOrThrow(id);
        try {
                minioService.deleteFile(fileManagement.getFilePath(), fileManagement.getBucketName());
            }catch (RuntimeException e){
            log.error("Error deleting file : {}", fileManagement.getFilePath());
        }
    }


    private FileMetadata getFileOrThrow(UUID fileId) {
        return fileManagementRepository.findById(fileId).orElseThrow(() -> new IllegalArgumentException(format(FILE_NOT_FOUND, fileId)));
    }


}
