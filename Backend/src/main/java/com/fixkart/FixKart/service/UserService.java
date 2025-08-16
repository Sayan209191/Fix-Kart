package com.fixkart.FixKart.service;

import com.fixkart.FixKart.entity.User.Users;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserService {
    Optional<Users> findByUsername(String username);
    String sendOtp(String mobileNumber);
    String signup(String mobileNumber, String password, int userCategory, String otp);
    String login(String mobileNumber, String otp);
}
