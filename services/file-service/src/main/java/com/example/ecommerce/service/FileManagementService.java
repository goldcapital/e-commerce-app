package com.example.ecommerce.service;

import com.example.ecommerce.FileManagementDTO;
import com.example.ecommerce.repository.FileManagementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.isd.commons.result.CommonResultData;

import static com.example.ecommerce.utile.FilesUtils.createPath;
import static com.example.ecommerce.utile.FilesUtils.getFileName;


@Service
@RequiredArgsConstructor
public class FileManagementService {
    private final MinioService minioService;
    private final FileManagementRepository fileManagementRepository;

    public CommonResultData<FileManagementDTO> uploadFile(MultipartFile file, String bucketName) {

        var fileName = getFileName(file.getOriginalFilename());
        var filePath = createPath(fileName);

        var genericResponse = minioService.uploadFile(file, bucketName, fileName);
        var saved=
        return null;
    }
}
