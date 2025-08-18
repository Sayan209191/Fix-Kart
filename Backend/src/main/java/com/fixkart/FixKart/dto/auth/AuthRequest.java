package com.fixkart.FixKart.dto.auth;

import com.fixkart.FixKart.entity.User.Role;
import lombok.Data;


@Data
public class AuthRequest {
    private String mobileNumber;
    private String password;
    private Long roleId;
}
