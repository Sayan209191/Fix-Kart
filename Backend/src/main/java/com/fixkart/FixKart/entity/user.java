package com.fixkart.FixKart.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mst_user")
@Data
public class user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer userCategory; // 1=Admin, 2=Customer, 3=Technician
}
