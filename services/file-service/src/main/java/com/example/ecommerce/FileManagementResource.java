package com.example.ecommerce;

import com.example.ecommerce.config.Constants;
import com.example.ecommerce.service.FileManagementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.isd.commons.result.CommonResultData;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileManagementResource {
    private  final FileManagementService fileManagementService;
    @PostMapping(value = "/upload")
    public CommonResultData<FileManagementDTO> uploadFile(@RequestParam(name = "file")
                                                          MultipartFile file) {
        return fileManagementService.uploadFile(file, Constants.BUCKET_NAME);
    }
}
