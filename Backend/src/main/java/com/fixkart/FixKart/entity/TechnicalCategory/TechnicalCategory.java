package com.fixkart.FixKart.entity.TechnicalCategory;

import com.fixkart.FixKart.entity.User.Technician;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String subCategory;
    @Column(nullable = true)
    private String Specialization;

    @OneToOne
    @JoinColumn(name = "technician_id", referencedColumnName = "id")
    private Technician technician;

}
