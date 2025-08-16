package com.fixkart.FixKart.controller;

import com.fixkart.FixKart.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/send-otp")
    public String sendOtp(@RequestParam String mobileNumber) {
        return userService.sendOtp(mobileNumber);
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String mobileNumber,
                         @RequestParam String password,
                         @RequestParam int userCategory,
                         @RequestParam String otp) {
        return userService.signup(mobileNumber, password, userCategory, otp);
    }

    @PostMapping("/login")
    public String login(@RequestParam String mobileNumber,
                        @RequestParam String otp) {
        return userService.login(mobileNumber, otp);
    }
}
