package com.marafloresdebach.services.impl;

import com.marafloresdebach.entities.User;
import com.marafloresdebach.repositories.UserRepository;
import com.marafloresdebach.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        // ✅ Validaciones previas
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (userRepository.existsByRun(user.getRun())) {
            throw new RuntimeException("RUN already exists");
        }

        // ✅ Aquí es el punto exacto del hash ANTES de guardar
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // ← aquí

        // ✅ Guardar en la BD
        return userRepository.save(user);
    }
    @Override
    public User update(Long id, User user) {
        User db = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        if (!db.getEmail().equals(user.getEmail())) {
            userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
                throw new RuntimeException("Email already exists");
            });
        }
        if (!db.getRun().equals(user.getRun())) {
            userRepository.findByRun(user.getRun()).ifPresent(u -> {
                throw new RuntimeException("RUN already exists");
            });
        }

        db.setRun(user.getRun());
        db.setName(user.getName());
        db.setLastName(user.getLastName());
        db.setBirthDate(user.getBirthDate());
        db.setEmail(user.getEmail());
        db.setAddress(user.getAddress());
        db.setRegion(user.getRegion());
        db.setComuna(user.getComuna());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            String raw = user.getPassword();
            if (raw.startsWith("$2a$") || raw.startsWith("$2b$") || raw.startsWith("$2y$")) {
                throw new RuntimeException("Password must be raw (not hashed)");
            }
            db.setPassword(passwordEncoder.encode(raw));
        }

        return userRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByRun(String run) {
        return userRepository.findByRun(run);
    }
}
