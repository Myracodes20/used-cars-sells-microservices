package com.usedcars.user.service;

import com.usedcars.user.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User register(User user);
    void deleteUser(Long id);
    String login(String email, String password);
}
