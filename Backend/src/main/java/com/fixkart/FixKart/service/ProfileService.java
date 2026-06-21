package com.fixkart.FixKart.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    String uploadProfilePhoto(MultipartFile file, String authHeader);
    String deleteProfilePhoto(String authHeader);
}

