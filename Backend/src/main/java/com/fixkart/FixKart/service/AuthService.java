package com.fixkart.FixKart.service;


public interface AuthService {
    String signup(String mobileNumber, String password, String confirmPassWord, long role);
    String signin(String mobileNumber, String password);
}
