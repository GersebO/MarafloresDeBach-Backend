package com.marafloresdebach.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marafloresdebach.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByRun(String run);
    boolean existsByEmail(String email);
    boolean existsByRun(String run);
}
