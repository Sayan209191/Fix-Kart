package com.fixkart.FixKart.serviceImpl.HelperServiceImpl;

import com.fixkart.FixKart.service.HelperService.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    private final String uploadDir = "uploads/";

    @Override
    public String saveFile(MultipartFile file, long id) {
        try {
            String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }
    @Override
    public String deleteFile(String fileName) {
        try{
            Path fullPath = Paths.get(uploadDir + fileName);
            File file = fullPath.toFile();

            if (file.exists()) {
                if (file.delete()) {
                    return "Photo Deleted Successfully";
                } else {
                    return "Failed to delete file";
                }
            } else {
                return "File not found";
            }

        }
        catch (Exception ex) {
            throw new RuntimeException("File Delete failed: " + ex.getMessage());
        }

    }
}
