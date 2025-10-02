package com.marafloresdebach.controllers;

import com.marafloresdebach.entities.Customer;
import com.marafloresdebach.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        Customer created = customerService.create(customer);
        return ResponseEntity
                .created(URI.create("/api/customers/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.update(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> list(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String run) {

        if (email != null && !email.isBlank()) {
            return customerService.getByEmail(email)
                    .map(c -> ResponseEntity.ok(List.of(c)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        }
        if (run != null && !run.isBlank()) {
            return customerService.getByRun(run)
                    .map(c -> ResponseEntity.ok(List.of(c)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        }
        return ResponseEntity.ok(customerService.getAll());
    }
}
