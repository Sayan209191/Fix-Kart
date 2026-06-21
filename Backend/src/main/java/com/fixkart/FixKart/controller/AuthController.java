package com.fixkart.FixKart.controller;

import com.fixkart.FixKart.dto.Otp.OtpLoginRequest;
import com.fixkart.FixKart.dto.Otp.OtpRequest;
import com.fixkart.FixKart.dto.auth.*;
import com.fixkart.FixKart.service.Authtentication.AuthService;
import com.fixkart.FixKart.service.Authtentication.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OtpService otpService;

    public AuthController(AuthService authService, OtpService otpService) {
        this.authService = authService;
        this.otpService = otpService;
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
        try{
            LoginResponse response = authService.signin(
                    request.getMobileNumber(),
                    request.getPassword()
            );
            return ResponseEntity.ok(response);
        }
        catch (Exception ex) {
            // Latter Implement Logger
            return ResponseEntity.badRequest().body(null);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        try{
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.replace("Bearer ", "");
                String response = authService.logout(token);
                return ResponseEntity.ok(response);
            }
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.badRequest().body("Invalid token");
    }

    // Send OTP (for login or forgot password)
    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody OtpRequest request) {
        try{
            String mobileNumber = request.getMobileNumber();
            boolean response = otpService.sendOtp(mobileNumber);
            if(!response) {
                throw new RuntimeException("Mobile not registered");
            } else {
                return ResponseEntity.ok("OTP sent to " + mobileNumber);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Verify OTP (for login)
    @PostMapping("/login-otp")
    public ResponseEntity<LoginResponse> loginWithOtp(@RequestBody OtpLoginRequest otpLoginRequest) {
        try {
            LoginResponse response = authService.loginViaOtp(otpLoginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // Forgot password flow -> Reset password after OTP
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try{
            String response = authService.resetPassword(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Password reset unsuccessful" + e.getMessage());
        }
    }
}
