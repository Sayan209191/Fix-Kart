package com.fixkart.FixKart.serviceImpl;

import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.repository.UserRepository;
import com.fixkart.FixKart.service.UserService;
import com.fixkart.FixKart.util.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // store OTP in memory (for testing)
    private final Map<String, String> otpStore = new HashMap<>();


    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByusername(username);
    }

    @Override
    public String sendOtp(String mobileNumber) {
        String otp = OtpUtil.generateOtp();
        otpStore.put(mobileNumber, otp);
        System.out.println("Generated OTP for " + mobileNumber + " = " + otp); // later replace with SMS API
        return "OTP sent to " + mobileNumber;
    }

    @Override
    public String signup(String mobileNumber, String password, int userCategory, String otp) {
        try {
            if(!(mobileNumber.length() == 10)) {
                return "Invalid Mobile Number";
            }
            if (!otpStore.containsKey(mobileNumber) || !otpStore.get(mobileNumber).equals(otp)) {
                return "Invalid or expired OTP";
            }

            if (userRepository.findByusername(mobileNumber).isPresent()) {
                return "User already exists";
            }

            Users user = new Users();
            try {
                user.setUsername(mobileNumber);
                user.setPassword(passwordEncoder.encode(password));
                user.setUserCategory(userCategory);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            userRepository.save(user);

            otpStore.remove(mobileNumber);
            return "Signup successful";
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    @Override
    public String login(String mobileNumber, String otp) {
        Optional<Users> userOpt = userRepository.findByusername(mobileNumber);
        if (userOpt.isEmpty()) {
            return "User not found";
        }

        if (!otpStore.containsKey(mobileNumber) || !otpStore.get(mobileNumber).equals(otp)) {
            return "Invalid or expired OTP";
        }

        otpStore.remove(mobileNumber);
        return "Login successful";
    }
}


