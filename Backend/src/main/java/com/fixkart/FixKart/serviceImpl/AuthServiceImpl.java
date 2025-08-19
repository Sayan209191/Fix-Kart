package com.fixkart.FixKart.serviceImpl;

import com.fixkart.FixKart.entity.User.Role;
import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.exception.InvalidArgumentException;
import com.fixkart.FixKart.repository.RoleRepository;
import com.fixkart.FixKart.repository.UserRepository;
import com.fixkart.FixKart.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String signup(String mobileNumber, String password, String confirmPassword, long roleId) {
        try {
            if (mobileNumber == null || mobileNumber.isEmpty()) {
                throw new InvalidArgumentException("Mobile number must not be empty.");
            }
            if(!mobileNumber.matches("\\d{10}")) {
                throw new InvalidArgumentException("Invalid mobile number. Must be exactly 10 digits.");
            }
            // Check Password and Confirm PassWord are matched or not

            // Change according to new Exception -> Useralready Exist
            if (userRepository.findByMobileNumber(mobileNumber).isPresent()) {
                return "User already exists with mobile number: " + mobileNumber;
            }

            try{
                Role selectedRole = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId));
                Users newUser = Users.builder()
                        .mobileNumber(mobileNumber)
                        .password(passwordEncoder.encode(password))
                        .role(selectedRole)
                        .build();

                userRepository.save(newUser);
                return "User registered successfully with mobile number: " + mobileNumber;
            }
            catch (Exception ex) {
                throw new RuntimeException("Signup failed: " + ex.getMessage(), ex);

            }

        } catch (Exception ex) {
            throw new RuntimeException("Signup failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public String signin(String mobileNumber, String password) {
        // use try catch block
        // Check Mobile Number is valid or not according to exception

        Users user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Change To Invalid Creaditial exception
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials";
        }

        // later replace with JWT token
        return "Login successful for: " + mobileNumber;
    }
}