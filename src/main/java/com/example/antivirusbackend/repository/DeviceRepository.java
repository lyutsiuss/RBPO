package com.example.antivirusbackend.repository;

import com.example.antivirusbackend.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
