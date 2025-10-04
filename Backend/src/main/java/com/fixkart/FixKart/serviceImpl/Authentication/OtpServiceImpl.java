package com.fixkart.FixKart.serviceImpl.Authentication;


import com.fixkart.FixKart.entity.User.OtpVerification;
import com.fixkart.FixKart.repository.OtpRepository;
import com.fixkart.FixKart.service.Authtentication.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {
    @Autowired
    private OtpRepository otpRepository;

    private final Random random = new Random();

    // Generate OTP and print to console
    @Override
    public String generateOtp(String mobileNumber) {
        String otp = String.format("%06d", random.nextInt(999999));

        OtpVerification otpEntity = new OtpVerification();
        otpEntity.setMobileNumber(mobileNumber);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);

        return otp;
    }

    // Verify OTP
    @Override
    public boolean verifyOtp(String mobileNumber, String otp) {
        return otpRepository.findTopByMobileNumberAndUsedFalseOrderByExpiryTimeDesc(mobileNumber)
                .filter(o -> o.getOtp().equals(otp) && o.getExpiryTime().isAfter(LocalDateTime.now()))
                .map(o -> {
                    o.setUsed(true);
                    otpRepository.save(o);
                    return true;
                })
                .orElse(false);
    }
    // Send OTP
    @Override
    public boolean sendOtp(String mobileNumber) {
        try{
            var otp = generateOtp(mobileNumber);
            System.out.println(otp);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
