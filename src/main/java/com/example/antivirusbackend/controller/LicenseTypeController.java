package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.LicenseType;
import com.example.antivirusbackend.service.LicenseTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licenseTypes")
public class LicenseTypeController {
    private final LicenseTypeService licenseTypeService;

    public LicenseTypeController(LicenseTypeService licenseTypeService) {
        this.licenseTypeService = licenseTypeService;
    }

    // Получить все типы лицензий
    @GetMapping
    public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeService.getAllLicenseTypes();
    }

    // Получить тип лицензии по ID
    @GetMapping("/{id}")
    public LicenseType getLicenseType(@PathVariable Long id) {
        return licenseTypeService.getLicenseTypeById(id);
    }

    // Создать новый тип лицензии
    @PostMapping
    public LicenseType createLicenseType(@RequestBody LicenseType licenseType) {
        return licenseTypeService.createLicenseType(licenseType);
    }

    // Обновить существующий тип лицензии
    @PutMapping("/{id}")
    public LicenseType updateLicenseType(@PathVariable Long id, @RequestBody LicenseType licenseType) {
        return licenseTypeService.updateLicenseType(id, licenseType);
    }

    // Удалить тип лицензии
    @DeleteMapping("/{id}")
    public String deleteLicenseType(@PathVariable Long id) {
        boolean deleted = licenseTypeService.deleteLicenseType(id);
        return deleted ? "LicenseType deleted" : "LicenseType not found";
    }
}
