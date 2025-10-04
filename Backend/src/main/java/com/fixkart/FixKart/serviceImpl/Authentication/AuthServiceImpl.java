package com.fixkart.FixKart.serviceImpl.Authentication;

import com.fixkart.FixKart.dto.Otp.OtpLoginRequest;
import com.fixkart.FixKart.dto.Profile.ProfileResponse;
import com.fixkart.FixKart.dto.Profile.ProfileUpdateRequest;
import com.fixkart.FixKart.dto.auth.LoginResponse;
import com.fixkart.FixKart.dto.auth.ResetPasswordRequest;
import com.fixkart.FixKart.dto.auth.SignupResponse;
import com.fixkart.FixKart.entity.Address.Address;
import com.fixkart.FixKart.entity.TechnicalCategory.TechnicalCategory;
import com.fixkart.FixKart.entity.User.Customer;
import com.fixkart.FixKart.entity.User.Role;
import com.fixkart.FixKart.entity.User.Technician;
import com.fixkart.FixKart.entity.User.Users;
import com.fixkart.FixKart.exception.InvalidArgumentException;
import com.fixkart.FixKart.exception.InvalidCredentialsException;
import com.fixkart.FixKart.repository.*;
import com.fixkart.FixKart.security.JwtUtil;
import com.fixkart.FixKart.service.*;
//import com.fixkart.FixKart.service.TokenBlacklistService;
import com.fixkart.FixKart.service.Authtentication.AuthService;
import com.fixkart.FixKart.service.Authtentication.OtpService;
import com.fixkart.FixKart.service.Authtentication.TokenBlacklistService;
import com.fixkart.FixKart.util.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private TechnicalCategoryRepository technicianCategoryRepository;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    private final TokenBlacklistService blacklistService;
//    private final PasswordUtil passwordUtil;
    @Autowired
    private OtpRepository otpRepository;

    private OtpService otpService;




    @Override
    public SignupResponse signup(String mobileNumber, String password, String confirmPassword, long roleId) {
        try {
            if (!password.equals(confirmPassword)) {
                throw  new InvalidCredentialsException("Passwords do not match");
//                return new SignupResponse("Passwords do not match", false);
            }
            // check PassWord match with password policy
            if(!PasswordUtil.isValidFormat(password)) {
                throw new InvalidArgumentException("Password new at least one number , one special character , one Uppercase character");
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
            if(mobileNumber == null || password == null) {
                return new LoginResponse("Login Failed", false, null, null);
            }
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new LoginResponse("Invalid credentials", false, null, null);
            }

            String token = jwtUtil.generateToken(mobileNumber);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("mobileNumber", user.getMobileNumber());
            userData.put("role", user.getRole().getName());
            return new LoginResponse("Login successful", true, token, userData);

        } catch (BadCredentialsException e) {
            return new LoginResponse(null, false, "Invalid credentials", null);
        } catch (Exception e) {
            return new LoginResponse(null, false, "Login failed: " + e.getMessage(), null);
        }
    }
    @Override
    public String logout(String token) {
        try{
            blacklistService.blacklistToken(token);
            return "Logged out successfully";
        }
        catch (Exception ex) {
            return "Logout Failed  /n" + ex.getMessage();
        }

    }

    @Override
    @Transactional
    public String editProfile(ProfileUpdateRequest profileUpdateRequest){ // alternative number, emailid, firstname, midname, lastname, state, addressline_1, addressline_2, pincode, landmark
        try{
            Users user = userRepository.findById(profileUpdateRequest.getUserID()).orElseThrow(() -> new RuntimeException("User not found"));
            Role role = user.getRole();

            Address address = new Address();
            //Handel Customer data
            if("CUSTOMER".equals(role.getName().toUpperCase().trim())) {
                Customer customer = customerRepository.findByUser(user).orElseThrow(() -> new RuntimeException("User Profile Data not found"));
                if(profileUpdateRequest.getFirstname() != null ) { customer.setFirstName(profileUpdateRequest.getFirstname()); }
                if(profileUpdateRequest.getMiddlename() != null ) { customer.setMiddleName(profileUpdateRequest.getMiddlename()); }
                if(profileUpdateRequest.getLastname() != null ) { customer.setLastName(profileUpdateRequest.getLastname());}
                if(profileUpdateRequest.getAlternativenumber() != null) { customer.setAlternativeMobileNumber(profileUpdateRequest.getAlternativenumber()); }
                if(profileUpdateRequest.getEmailID() != null) { customer.setEmailId(profileUpdateRequest.getEmailID());}

                // Saving Customer Data
                customerRepository.save(customer);

                // Find the address object or that customer
                address = customer.getAddress();
            }
            // Handel Technician Specific data
            else if("TECHNICIAN".equals(role.getName().toUpperCase().trim())) {
                Technician technician = technicianRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Profile Data not Found !"));
                if(profileUpdateRequest.getFirstname() != null ) { technician.setFirstName(profileUpdateRequest.getFirstname()); }
                if(profileUpdateRequest.getMiddlename() != null ) { technician.setMiddleName(profileUpdateRequest.getMiddlename()); }
                if(profileUpdateRequest.getLastname() != null ) { technician.setLastName(profileUpdateRequest.getLastname()); }
                if(profileUpdateRequest.getAlternativenumber() != null) { technician.setAlternativeMobileNumber(profileUpdateRequest.getAlternativenumber()); }
                if(profileUpdateRequest.getEmailID() != null) { technician.setEmailId(profileUpdateRequest.getEmailID()); }
                // Set Technical Specific Field
                TechnicalCategory technicalCategory = technicianCategoryRepository.findByTechnician(technician).orElseThrow(() -> new RuntimeException("Technical Profile Data not Found !"));
                if(profileUpdateRequest.getCategory() != null) { technicalCategory.setCategory(profileUpdateRequest.getCategory());}
                if(profileUpdateRequest.getSubCategory() != null) {technicalCategory.setSubCategory(profileUpdateRequest.getSubCategory()); }
                if(profileUpdateRequest.getSpecialization() != null) { technicalCategory.setSpecialization(profileUpdateRequest.getSpecialization());}
                // Save Technician Data
                technicalCategory.setTechnician(technician);
                technicianCategoryRepository.save(technicalCategory);
                // Save Technician Data
                technicianRepository.save(technician);
                // find address object of technician
                address = technician.getAddress();

            }
            // Handel Address data
            if(profileUpdateRequest.getAddressLine1() != null) { address.setAddressLine1(profileUpdateRequest.getAddressLine1()); }
            if(profileUpdateRequest.getAddressLine2() != null) {address.setAddressLine2(profileUpdateRequest.getAddressLine2()); }
            if(profileUpdateRequest.getCity() != null) {address.setCity(profileUpdateRequest.getCity()); }
            if(profileUpdateRequest.getPincode() != null) { address.setPincode(profileUpdateRequest.getPincode());}
            if(profileUpdateRequest.getLandmark() != null) { address.setLandmark(profileUpdateRequest.getLandmark());}

            addressRepository.save(address);
            return "Profile Update Successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public ProfileResponse getProfile(String token) {
        try{
            String mobileNumber = jwtUtil.extractUsername(token);

            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            ProfileResponse response = new ProfileResponse();
            response.setId(user.getId());
            response.setMobileNumber(user.getMobileNumber());
            response.setRole(user.getRole().getName());

            // fetch customer or technician details
            if ("CUSTOMER".equalsIgnoreCase(user.getRole().getName())) {
                Customer customer = customerRepository.findByUser(user).orElse(null);
                Address address = customer != null ? customer.getAddress() : null;
                response.setAddress(address);
                response.setCustomerDetails(customer);
            } else if ("TECHNICIAN".equalsIgnoreCase(user.getRole().getName())) {
                Technician technician = technicianRepository.findByUser(user).orElse(null);
                Address address = technician != null ? technician.getAddress() : null;
                response.setTechnicianDetails(technician);
            }
            return response;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public LoginResponse loginViaOtp(OtpLoginRequest otpLoginRequest) {
        try{
            String mobileNumber = otpLoginRequest.getMobileNumber();
            String otp = otpLoginRequest.getOtp();
            if(mobileNumber == null || otp == null) {
                return new LoginResponse("Login Failed", false, null, null);
            }
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtUtil.generateToken(mobileNumber);

            Map<String, Object> userData = new HashMap<>();
            userData.put("id", user.getId());
            userData.put("mobileNumber", user.getMobileNumber());
            userData.put("role", user.getRole().getName());
            return new LoginResponse("Login successful", true, token, userData);
        } catch (BadCredentialsException e) {
            return new LoginResponse(null, false, "Invalid credentials", null);
        } catch (Exception e) {
            return new LoginResponse(null, false, "Login failed: " + e.getMessage(), null);
        }
    }

    @Override
    public String resetPassword(ResetPasswordRequest resetPasswordRequest) {
        try{
            String mobileNumber = resetPasswordRequest.getMobileNumber();
            String otp = resetPasswordRequest.getOtp();
            String newPassword = resetPasswordRequest.getNewPassword();

            if (!otpService.verifyOtp(mobileNumber, otp)) {
                return "Invalid OTP";
            }
            Users user = userRepository.findByMobileNumber(mobileNumber)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Password reset fails" + e.getMessage());
        }
        return "Password Reset Successfully";
    }




}
