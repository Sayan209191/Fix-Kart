package com.fixkart.FixKart.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mst_user")
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer userCategory; // 1=Admin, 2=Customer, 3=Technician

    public void setUserCategory(int userCategory) {
        this.userCategory = userCategory;
    }
}
