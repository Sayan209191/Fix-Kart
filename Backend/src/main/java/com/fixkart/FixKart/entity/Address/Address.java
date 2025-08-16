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
    @Column(nullable = false)
    private String addressLine1;
    @Column(nullable = true)
    private String addressLine2;
    @Column(nullable = false)
    private Long pincode;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String State;
    @Column(nullable = false)
    private String landmark;
}
