package com.fixkart.FixKart.service.Authtentication;


import com.fixkart.FixKart.dto.Otp.OtpLoginRequest;
import com.fixkart.FixKart.dto.Profile.ProfileResponse;
import com.fixkart.FixKart.dto.Profile.ProfileUpdateRequest;
import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.ResetPasswordRequest;
import com.fixkart.FixKart.dto.auth.SignupResponse;

public interface AuthService {
    SignupResponse signup(String mobileNumber, String password, String confirmPassWord, long role);
    LoginResponse signin(String mobileNumber, String password);
    String logout(String token);
    String editProfile(ProfileUpdateRequest profileUpdateRequest);
    ProfileResponse getProfile(String token);
    LoginResponse loginViaOtp(OtpLoginRequest otpLoginRequest);
    String resetPassword(ResetPasswordRequest resetPasswordRequest);
}
