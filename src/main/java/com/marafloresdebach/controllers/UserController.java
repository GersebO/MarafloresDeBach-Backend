package com.marafloresdebach.controllers;

import com.marafloresdebach.entities.User;
import com.marafloresdebach.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        User created = userService.create(user);
        return ResponseEntity
                .created(URI.create("/api/users/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> list(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String run) {

        if (email != null && !email.isBlank()) {
            return userService.getByEmail(email)
                    .map(u -> ResponseEntity.ok(List.of(u)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        }
        if (run != null && !run.isBlank()) {
            return userService.getByRun(run)
                    .map(u -> ResponseEntity.ok(List.of(u)))
                    .orElseGet(() -> ResponseEntity.ok(List.of()));
        }
        return ResponseEntity.ok(userService.getAll());
    }
}
