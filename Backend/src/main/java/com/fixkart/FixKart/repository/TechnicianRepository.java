package com.fixkart.FixKart.repository;

import com.fixkart.FixKart.entity.User.Technician;
import com.fixkart.FixKart.entity.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Optional<Technician> findByUser(Users user);
}