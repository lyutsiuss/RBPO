package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.LicenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LicenseTypeRepository extends JpaRepository<LicenseType, Long> {
    Optional<LicenseType> findByName(String name);
}
