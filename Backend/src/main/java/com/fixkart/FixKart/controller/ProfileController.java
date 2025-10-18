package com.fixkart.FixKart.controller;


import com.fixkart.FixKart.dto.Profile.ProfileResponse;
import com.fixkart.FixKart.dto.Profile.ProfileUpdateRequest;
import com.fixkart.FixKart.service.Authtentication.AuthService;
import com.fixkart.FixKart.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final AuthService authService;
    private final ProfileService profileService;

    public ProfileController(AuthService authService, ProfileService profileService) {
        this.authService = authService;
        this.profileService = profileService;
    }
    @PostMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest) { // what kind of data comes have to check , then implement
        try{
            String response = authService.editProfile(profileUpdateRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
    @GetMapping("/profile-me")
    public ResponseEntity<ProfileResponse> getProfile(@RequestHeader("Authorization") String authHeader) {
        try{
            String token = authHeader.replace("Bearer ", "");
            ProfileResponse response = authService.getProfile(token);
            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }

    }
    @PostMapping("/upload-image")
    public ResponseEntity<String> uploadImage(@RequestHeader("Authorization") String authHeader, @RequestParam("image") MultipartFile file) {
        try {
            profileService.uploadProfilePhoto(file, authHeader);
            return ResponseEntity.ok("Image uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/delete-image")
    public ResponseEntity<?> deleteImage(@RequestHeader("Authorization") String authHeader) {
        try{
            // verify token → find user
            profileService.deleteProfilePhoto(authHeader);
            // delete file from storage and clear imagePath
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }

        return ResponseEntity.ok("Deleted");
    }
}
