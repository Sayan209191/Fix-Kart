package com.fixkart.FixKart.dto.auth;

import lombok.Data;

@Data
public class SignupRequest {
    private String mobileNumber;
    private String password;
    private long roleId;   // can be "USER", "ADMIN", etc.
}