package com.example.ecommerce;

import com.example.ecommerce.config.Constants;
import com.example.ecommerce.dto.response.FileManagementDTO;
import com.example.ecommerce.service.FileManagementService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.isd.commons.result.CommonResultData;

import java.util.UUID;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
public class FileManagementController {
    private final FileManagementService service;

    @PostMapping(value = "/upload")
    public CommonResultData<FileManagementDTO> uploadFile(@RequestParam(name = "file")
                                                          MultipartFile file) {
        return service.uploadFile(file, Constants.BUCKET_NAME);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<CommonResultData<FileManagementDTO>> downloadFile(@PathVariable UUID id,
                                                                            HttpServletResponse servletResponse) {
        return ResponseEntity.ok(service.downloadFile(id, servletResponse));
    }

    @DeleteMapping("/delete-by/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") UUID id) {
        service.deleteFile(id);
        return ResponseEntity.accepted().build();
    }
}
