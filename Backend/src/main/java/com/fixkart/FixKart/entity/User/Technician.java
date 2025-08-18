package com.fixkart.FixKart.entity.User;

import jakarta.persistence.*;
import lombok.*;
import com.fixkart.FixKart.entity.Address.Address;
import com.fixkart.FixKart.entity.TechnicalCategory.TechnicalCategory;

@Entity
@Table(name = "t_technician")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Technician {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mst_user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_technician_user"))
    private Users user;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String middleName;

    @Column(nullable = true)
    private String lastName;

//    @Column(nullable = false, unique = true)
//    private Long mobileNumber;

    @Column(nullable = true)
    private Long alternativeMobileNumber;

    @Column(nullable = false, unique = true)
    private String emailId;

    @OneToOne
    @JoinColumn(name = "mst_address_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_technician_address"))
    private Address address;

    @ManyToOne
    @JoinColumn(name = "mst_category_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_technician_category"))
    private TechnicalCategory category;
}
