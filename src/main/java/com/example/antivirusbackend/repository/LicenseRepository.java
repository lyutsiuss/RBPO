package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseRepository extends JpaRepository<License, Long> {
    Optional<License> findByCode(String code);
}
