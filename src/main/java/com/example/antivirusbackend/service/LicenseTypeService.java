package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.LicenseType;
import com.example.antivirusbackend.repository.LicenseTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseTypeService {
    private final LicenseTypeRepository licenseTypeRepository;

    public LicenseTypeService(LicenseTypeRepository licenseTypeRepository) {
        this.licenseTypeRepository = licenseTypeRepository;
    }

    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    public LicenseType getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id).orElse(null);
    }

    public LicenseType createLicenseType(LicenseType licenseType) {
        // Проверка на уникальность имени типа лицензии
        Optional<LicenseType> existing = licenseTypeRepository.findByName(licenseType.getName());
        if (existing.isPresent()) {
            throw new RuntimeException("LicenseType with this name already exists");
        }
        return licenseTypeRepository.save(licenseType);
    }

    public LicenseType updateLicenseType(Long id, LicenseType updatedLicenseType) {
        return licenseTypeRepository.findById(id).map(lt -> {
            lt.setName(updatedLicenseType.getName());
            lt.setDescription(updatedLicenseType.getDescription());
            lt.setDefaultDuration(updatedLicenseType.getDefaultDuration());
            return licenseTypeRepository.save(lt);
        }).orElseThrow(() -> new RuntimeException("LicenseType not found"));
    }

    public boolean deleteLicenseType(Long id) {
        if (licenseTypeRepository.existsById(id)) {
            licenseTypeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
