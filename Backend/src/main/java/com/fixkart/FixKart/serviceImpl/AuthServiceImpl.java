package com.fixkart.FixKart.serviceImpl;

import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.SignupResponse;
import com.fixkart.FixKart.entity.User.Role;
import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.repository.RoleRepository;
import com.fixkart.FixKart.repository.UserRepository;
import com.fixkart.FixKart.security.JwtUtil;
import com.fixkart.FixKart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public SignupResponse signup(String mobileNumber, String password, String confirmPassword, long roleId) {
        if (!password.equals(confirmPassword)) {
            return new SignupResponse("Passwords do not match", false);
        }

        if (userRepository.findByMobileNumber(mobileNumber).isPresent()) {
            return new SignupResponse("User already exists", false);
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Invalid role id"));

        Users user = new Users();
        user.setMobileNumber(mobileNumber);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);

        userRepository.save(user);

        return new SignupResponse("Signup successful", true);
    }

    @Override
    public LoginResponse signin(String mobileNumber, String password) {
        try {
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new LoginResponse("Invalid credentials", false, null);
            }

            String token = jwtUtil.generateToken(mobileNumber);

            return new LoginResponse("Login successful", true, token);

        } catch (BadCredentialsException e) {
            return new LoginResponse(null, false, "Invalid credentials");
        } catch (Exception e) {
            return new LoginResponse(null, false, "Login failed: " + e.getMessage());
        }
    }
}
