package com.fixkart.FixKart.dto.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    String mobileNumber;
    String otp;
    String newPassword;
}
