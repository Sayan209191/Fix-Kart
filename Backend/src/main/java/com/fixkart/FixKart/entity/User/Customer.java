package com.fixkart.FixKart.entity.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fixkart.FixKart.entity.Address.Address;

@Entity
@Table(name = "t_customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mst_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_customer_user"))
    private Users user;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = false, unique = true)
    private Long mobileNumber;
    @Column(nullable = false)
    private Long alternativeMobileNumber;

    @Column(nullable = true, unique = true)
    private String emailId;
    @OneToOne
    @JoinColumn(name = "mst_address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_customer_address"))
    private Address address;
}