package com.fixkart.FixKart.serviceImpl;

import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.SignupResponse;
import com.fixkart.FixKart.entity.Address.Address;
import com.fixkart.FixKart.entity.User.Customer;
import com.fixkart.FixKart.entity.User.Role;
import com.fixkart.FixKart.entity.User.Technician;
import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.repository.*;
import com.fixkart.FixKart.security.JwtUtil;
import com.fixkart.FixKart.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public SignupResponse signup(String mobileNumber, String password, String confirmPassword, long roleId) {
        try {
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

            Users savedUser = userRepository.save(user);

            // Create Empty Address Field
            Address address = new Address();
            addressRepository.save(address);

            // Create empty profile based on role
            if ("CUSTOMER".equalsIgnoreCase(role.getName())) {
                Customer customer = new Customer();
                customer.setUser(savedUser);
                customer.setAddress(address);

                customerRepository.save(customer);
            } else if ("TECHNICIAN".equalsIgnoreCase(role.getName())) {
                Technician technician = new Technician();
                technician.setUser(savedUser);
                technician.setAddress(address);
                technicianRepository.save(technician);
            }

            return new SignupResponse("Signup successful", true);
        }
        catch (Exception ex) {
            return new SignupResponse(ex.getMessage() , false);
        }
    }


    @Override
    public LoginResponse signin(String mobileNumber, String password) {
        try {
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new LoginResponse("Invalid credentials", false, null, null);
            }

            String token = jwtUtil.generateToken(mobileNumber);

            Map<String, Object> userData = new HashMap<>();
//            userData.put("id", user.getId());
            userData.put("mobileNumber", user.getMobileNumber());
            userData.put("role", user.getRole().getName());

            // Attach customer/technician data if exists
            if ("CUSTOMER".equalsIgnoreCase(user.getRole().getName())) {
                Customer customer = customerRepository.findByUser(user).orElse(null);
                userData.put("Customer Details", customer);
//                if(customer != null){
//                    Address customerAddress = customer.getAddress();
//                    userData.put("Customer Address", customerAddress);
//                }


            } else if ("TECHNICIAN".equalsIgnoreCase(user.getRole().getName())) {
                Technician technician = technicianRepository.findByUser(user).orElse(null);
                userData.put("technicianDetails", technician);
//                if(technician  != null) {
//                    Address technicianAddress = technician.getAddress();
//                    userData.put("Technician Address", technicianAddress);
//                }
            }

            return new LoginResponse("Login successful", true, token, userData);

        } catch (BadCredentialsException e) {
            return new LoginResponse(null, false, "Invalid credentials", null);
        } catch (Exception e) {
            return new LoginResponse(null, false, "Login failed: " + e.getMessage(), null);
        }
    }

}
