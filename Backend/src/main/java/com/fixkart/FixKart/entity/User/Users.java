package com.fixkart.FixKart.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mst_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mobile_number", nullable = false, unique = true)
    private String mobileNumber;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)   // many users → one role
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}