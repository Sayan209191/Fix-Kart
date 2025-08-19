package com.fixkart.FixKart.controller;


import com.fixkart.FixKart.dto.auth.LoginRequest;
import com.fixkart.FixKart.service.AuthService;
import com.fixkart.FixKart.dto.auth.AuthRequest;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        return authService.signup(request.getMobileNumber(), request.getPassword(), request.getConfirmPassword(), request.getRoleId());
    }

    @PostMapping("/signin")
    public String signin(@RequestBody LoginRequest request) {
        return authService.signin(request.getMobileNumber(), request.getPassword());
    }

    // Logout (optional for JWT, usually handled client-side by deleting token)
    @PostMapping("/logout")
    public String logout() {
        return "Logout successful...\n";
    }
}