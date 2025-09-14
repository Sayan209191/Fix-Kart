package com.fixkart.FixKart.repository;

import com.fixkart.FixKart.entity.User.Customer;
import com.fixkart.FixKart.entity.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(Users user);
}