package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.LicenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LicenseHistoryRepository extends JpaRepository<LicenseHistory, Long> {
}
