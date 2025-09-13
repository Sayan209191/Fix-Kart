package com.fixkart.FixKart.controller;


import com.fixkart.FixKart.dto.Profile.ProfileResponse;
import com.fixkart.FixKart.dto.Profile.ProfileUpdateRequest;
import com.fixkart.FixKart.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private final AuthService authService;

    public ProfileController(AuthService authService) {
        this.authService = authService;
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
        String token = authHeader.replace("Bearer ", "");
        ProfileResponse response = authService.getProfile(token);
        return ResponseEntity.ok(response);
    }
}
