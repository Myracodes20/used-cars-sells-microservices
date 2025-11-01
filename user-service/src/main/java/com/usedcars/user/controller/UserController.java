package com.usedcars.user.controller;

import com.usedcars.user.model.User;
import com.usedcars.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // register expects JSON body
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        System.out.println(">>> Register endpoint hit: " + user.getEmail());
        return ResponseEntity.ok(userService.register(user));
    }

    // canonical login endpoint expects JSON body { "email": "...", "password": "..." }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        System.out.println(">>> Login endpoint hit: " + user.getEmail());
        String token = userService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(token);
    }

    // Protected routes
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
