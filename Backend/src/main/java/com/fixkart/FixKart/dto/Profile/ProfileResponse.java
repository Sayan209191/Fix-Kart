package com.fixkart.FixKart.dto.Profile;

import com.fixkart.FixKart.entity.Address.Address;
import com.fixkart.FixKart.entity.User.Customer;
import com.fixkart.FixKart.entity.User.Technician;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private Long id;
    private String mobileNumber;
    private String role;
    private Customer customerDetails;
    private Technician technicianDetails;
    private Address address;
}
