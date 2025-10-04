package com.fixkart.FixKart.service.Authtentication;

public interface OtpService {
    String generateOtp(String mobileNumber);
    boolean verifyOtp(String mobileNumber, String otp);
    boolean sendOtp(String mobileNumber);
}
