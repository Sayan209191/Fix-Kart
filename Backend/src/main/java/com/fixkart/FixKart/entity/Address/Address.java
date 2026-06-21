package com.fixkart.FixKart.entity.Address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String addressLine1;
    @Column(nullable = true)
    private String addressLine2;
    @Column(nullable = true)
    private Long pincode;
    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String state;
    @Column(nullable = true)
    private String landmark;
}
