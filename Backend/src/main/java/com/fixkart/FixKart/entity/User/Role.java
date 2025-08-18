package com.fixkart.FixKart.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "t_role_manager")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long role_id;

    @Column(nullable = false, unique = true)
    private String name;   // e.g., ADMIN, CUSTOMER, TECHNICIAN
}

