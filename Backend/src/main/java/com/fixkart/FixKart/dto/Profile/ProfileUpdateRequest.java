package com.fixkart.FixKart.dto.Profile;

import lombok.*;

@Data
@AllArgsConstructor
public class ProfileUpdateRequest {
    private Integer userID; // mst_user_id
    // Profile Data
    private String firstname;
    private String middlename;
    private String lastname;
    private Long alternativenumber;
    private String emailID;
    // Address Data
    private String addressLine1;
    private String addressLine2;
    private Long pincode;
    private String city;
    private String state;
    private String landmark;

    // Technician Specific field
    private String category;
    private String subCategory;
    private String specialization;
}
