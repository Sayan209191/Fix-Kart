package com.fixkart.FixKart.entity.User;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_otp_verification")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileNumber;
    private String otp;
    private LocalDateTime expiryTime;
    private boolean used = false;
}

