package com.fixkart.FixKart.service.HelperService;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String saveFile(MultipartFile file);
}
