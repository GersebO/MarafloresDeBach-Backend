package com.marafloresdebach.services;

import com.marafloresdebach.entities.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    Customer create(Customer customer);
    Customer update(Long id, Customer customer);
    void delete(Long id);

    Customer getById(Long id);
    List<Customer> getAll();

    Optional<Customer> getByEmail(String email);
    Optional<Customer> getByRun(String run);
    
}
