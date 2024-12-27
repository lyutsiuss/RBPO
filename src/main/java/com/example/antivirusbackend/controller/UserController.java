package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.User;
import com.example.antivirusbackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: 1. Добавить авторизацию
//TODO: 2. getAllUsers - вернёт и все пароли?

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        return deleted ? "User deleted" : "User not found";
    }
}
