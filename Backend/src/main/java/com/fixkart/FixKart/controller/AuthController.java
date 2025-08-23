package com.fixkart.FixKart.controller;

import com.fixkart.FixKart.dto.auth.LoginRequest;
import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.SignupRequest;
import com.fixkart.FixKart.dto.auth.SignupResponse;
import com.fixkart.FixKart.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(
                request.getMobileNumber(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getRoleId()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> signin(@RequestBody LoginRequest request) {
        LoginResponse response = authService.signin(
                request.getMobileNumber(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);
    }
}
