package com.fixkart.FixKart.dto.Otp;

import lombok.Data;

@Data
public class OtpLoginRequest {
    String mobileNumber;
    String otp;
}
