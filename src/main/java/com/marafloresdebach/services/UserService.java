package com.marafloresdebach.services;
import com.marafloresdebach.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User create(User user);
    User update(Long id, User user);
    void delete(Long id);

    User getById(Long id);
    List<User> getAll();

    Optional<User> getByEmail(String email);
    Optional<User> getByRun(String run);
    
}
