package com.fixkart.FixKart.service;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    String uploadImage(MultipartFile file, String authHeader);
}

