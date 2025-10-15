package com.fixkart.FixKart.serviceImpl;


import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.repository.UserRepository;
import com.fixkart.FixKart.security.JwtUtil;
import com.fixkart.FixKart.service.HelperService.FileUploadService;
import com.fixkart.FixKart.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public String uploadImage(MultipartFile file, String authHeader){
        try{
            String token = authHeader.replace("Bearer ", "").trim();

            // Extract username from JWT
            String mobileNumber = jwtUtil.extractMobileNumber(token);

            // Find user by Mobile Number
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));
//
//            // Save file locally
            String fileName = fileUploadService.saveFile(file);
//
//            // Update user’s image path in DB
            user.setImagePath("/uploads/" + fileName);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }
}
