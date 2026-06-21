package com.fixkart.FixKart.dto.auth;

import lombok.Data;


@Data
public class LoginRequest {
    private String mobileNumber;
    private String password;
}
