package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.DeviceLicense;
import com.example.antivirusbackend.entity.License;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceLicenseRepository extends JpaRepository<DeviceLicense, Long> {
    List<DeviceLicense> findByLicense(License license);
}
