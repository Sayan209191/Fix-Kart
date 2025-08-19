package com.fixkart.FixKart.repository;
import com.fixkart.FixKart.entity.User.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByMobileNumber(String mobileNumber);
    boolean existsByMobileNumber(String mobileNumber);
}
