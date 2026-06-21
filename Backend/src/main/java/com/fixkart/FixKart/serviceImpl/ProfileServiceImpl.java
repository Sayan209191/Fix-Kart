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
    public String uploadProfilePhoto(MultipartFile file, String authHeader){
        try{
            if (file.isEmpty()) throw new IllegalArgumentException("File is empty");
            String token = authHeader.replace("Bearer ", "").trim();

            // Extract username from JWT
            String mobileNumber = jwtUtil.extractMobileNumber(token);

            // Find user by Mobile Number
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            // Delete Previous Photo
            if(user.getImagePath() != null) {
                fileUploadService.deleteFile(user.getImagePath());
            }
            // Save file locally
            String fileName = fileUploadService.saveFile(file, user.getId());

           // Update user’s image path in DB
            user.setImagePath("/uploads/" + fileName);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Profile Photo Upload Successfully";
    }
    @Override
    public String deleteProfilePhoto(String authHeader) {
        try{
            String token = authHeader.replace("Bearer ", "").trim();

            // Extract username from JWT
            String mobileNumber = jwtUtil.extractMobileNumber(token);

            // Find user by Mobile Number
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            if (user.getImagePath() != null) {
                String imagePath = user.getImagePath().replace("/uploads/", "");
                fileUploadService.deleteFile(imagePath);
                user.setImagePath(null);
                userRepository.save(user);
            }
            else {
                return "No Profile Picture Uploaded Previously";
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "Profile Photo deleted Successfully";
    }
}
