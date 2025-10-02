package com.marafloresdebach.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marafloresdebach.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByRun(String run);
    boolean existsByEmail(String email);
    boolean existsByRun(String run);
}
