package com.example.prodcut;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service",
        url = "")
public interface FileClint {
  //  @PostMapping(value = "/upload")
   // CommonResultData<FileManagementDTO> uploadFile(@RequestParam(name = "file") MultipartFile file);

}
