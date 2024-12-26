package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.User;
import com.example.antivirusbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updated) {
        return userRepository.findById(id).map(u -> {
            u.setLogin(updated.getLogin());
            u.setPasswordHash(updated.getPasswordHash());
            u.setEmail(updated.getEmail());
            u.setRole(updated.getRole());
            return userRepository.save(u);
        }).orElse(null);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
