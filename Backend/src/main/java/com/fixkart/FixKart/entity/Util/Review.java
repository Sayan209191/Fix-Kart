package com.fixkart.FixKart.entity.Util;

import com.fixkart.FixKart.entity.User.Customer;
import com.fixkart.FixKart.entity.User.Technician;
import com.fixkart.FixKart.entity.User.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mst_review")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reviewer (Customer)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_review_customer"))
    private Customer customer;

    // Optional: reviewed technician
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_review_technician"))
    private Technician technician;

    // Optional: reviewed service
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_review_service"))
    private Service service;

    @Column(nullable = false)
    private int rating; // e.g. 1–5 stars

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private LocalDateTime reviewDate = LocalDateTime.now();

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewImage> images;

}
