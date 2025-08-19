package com.fixkart.FixKart.serviceImpl;

import com.fixkart.FixKart.entity.User.Role;
import com.fixkart.FixKart.entity.User.Users;
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
    public String signup(String mobileNumber, String password, long roleId) {
        try {
            if (userRepository.findByMobileNumber(mobileNumber).isPresent()) {
                return "User already exists with mobile number: " + mobileNumber;
            }


            Role selectedRole = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + roleId)); // Change it to custom extension

            Users newUser = Users.builder()
                    .mobileNumber(mobileNumber)
                    .password(passwordEncoder.encode(password))
                    .role(selectedRole)
                    .build();

            userRepository.save(newUser);

            return "User registered successfully with mobile number: " + mobileNumber;

        } catch (Exception ex) {
            throw new RuntimeException("Signup failed: " + ex.getMessage(), ex);
        }
    }
    @Override
    public String signin(String mobileNumber, String password) {
        Users user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials";
        }

        // later replace with JWT token
        return "Login successful for: " + mobileNumber;
    }
}