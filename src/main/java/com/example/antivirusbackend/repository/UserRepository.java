package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
