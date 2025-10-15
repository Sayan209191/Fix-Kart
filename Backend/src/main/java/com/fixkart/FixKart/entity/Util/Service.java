package com.fixkart.FixKart.entity.Util;

import com.fixkart.FixKart.entity.TechnicalCategory.TechnicalCategory;
import com.fixkart.FixKart.entity.User.Technician;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mst_service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_service_technician"))
    private Technician technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_service_category"))
    private TechnicalCategory category;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private Boolean isActive = true;
}
