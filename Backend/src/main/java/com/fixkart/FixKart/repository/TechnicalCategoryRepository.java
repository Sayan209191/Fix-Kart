package com.fixkart.FixKart.repository;

import com.fixkart.FixKart.entity.TechnicalCategory.TechnicalCategory;
import com.fixkart.FixKart.entity.User.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface TechnicalCategoryRepository extends JpaRepository<TechnicalCategory, Long> {
    Optional<TechnicalCategory> findByTechnician(Technician technician);
}



