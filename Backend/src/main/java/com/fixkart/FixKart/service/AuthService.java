package com.fixkart.FixKart.service;


import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.SignupResponse;

public interface AuthService {
    SignupResponse signup(String mobileNumber, String password, String confirmPassWord, long role);
    LoginResponse signin(String mobileNumber, String password);
}
