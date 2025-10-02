package com.marafloresdebach.services.impl;

import com.marafloresdebach.entities.Customer;
import com.marafloresdebach.repositories.CustomerRepository;
import com.marafloresdebach.services.CustomerService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder; // 👈 inyectado aquí

    @Override
    public Customer create(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (customerRepository.existsByRun(customer.getRun())) {
            throw new RuntimeException("RUN already exists");
        }

        // ✅ Aquí igual, ANTES de guardar
        if (customer.getPassword() == null || customer.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword())); // ← aquí

        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Customer db = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));

        // Si cambia email, valida unicidad
        if (!db.getEmail().equals(customer.getEmail())) {
            customerRepository.findByEmail(customer.getEmail()).ifPresent(c -> {
                throw new RuntimeException("Email already exists");
            });
        }
        // Si cambia run, valida unicidad (si implementaste findByRun)
        if (!db.getRun().equals(customer.getRun())) {
            getByRun(customer.getRun()).ifPresent(c -> {
                throw new RuntimeException("RUN already exists");
            });
        }

        db.setRun(customer.getRun());
        db.setName(customer.getName());
        db.setLastName(customer.getLastName());
        db.setBirthDate(customer.getBirthDate());
        db.setEmail(customer.getEmail());
        db.setAddress(customer.getAddress());
        db.setRegion(customer.getRegion());
        db.setComuna(customer.getComuna());
        db.setPassword(customer.getPassword()); // luego puedes encriptar

        return customerRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RuntimeException("Customer not found: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getByRun(String run) {
        // requiere agregar findByRun en el repo
        return customerRepository.findByRun(run);
    }
}
