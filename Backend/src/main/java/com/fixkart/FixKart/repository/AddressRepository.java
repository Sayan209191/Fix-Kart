package com.fixkart.FixKart.repository;

import com.fixkart.FixKart.entity.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
//    Optional<Address> findByAddress(Address address);
}
