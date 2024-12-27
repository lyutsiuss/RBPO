package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.dto.Ticket;
import com.example.antivirusbackend.entity.License;
import com.example.antivirusbackend.service.LicenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: 1. Нужно добавить авторизацию
//TODO: 2. createLicense - кто вам присылает код? Он должен генерироваться

@RestController
@RequestMapping("/licenses")
public class LicenseController {
    private final LicenseService licenseService;

    public LicenseController(LicenseService licenseService) {
        this.licenseService = licenseService;
    }

    @GetMapping
    public List<License> getAllLicenses() {
        return licenseService.getAll();
    }

    @GetMapping("/{id}")
    public License getLicense(@PathVariable Long id) {
        return licenseService.getById(id);
    }

    @PostMapping
    public License createLicense(@RequestParam String code, @RequestParam Long userId) {
        return licenseService.createLicense(code, userId);
    }

    @PutMapping("/{id}")
    public License updateLicense(@PathVariable Long id, @RequestParam String code) {
        return licenseService.updateLicense(id, code);
    }

    @DeleteMapping("/{id}")
    public String deleteLicense(@PathVariable Long id) {
        boolean deleted = licenseService.deleteLicense(id);
        return deleted ? "License deleted" : "License not found";
    }

    // Метод получения Ticket по ID
    @GetMapping("/{id}/ticket")
    public Ticket getTicket(@PathVariable Long id) {
        License license = licenseService.getById(id);
        if (license == null) {
            throw new RuntimeException("License not found");
        }
        // Данный метод не знает устройство, передадим null
        return new Ticket() {{
            setServerDate(java.time.LocalDateTime.now());
            setLifeTimeSeconds(300);
            setActivationDate(license.getFirstActivationDate());
            setEndingDate(license.getEndingDate());
            setUserId(license.getUser().getId()); // Устанавливаем userId из поля user
            setDeviceId("no-device-info");
            setBlocked(license.isBlocked());
            setDigitalSignature("signature-placeholder");
        }};
    }

    // Новый эндпоинт для полного создания лицензии
    @PostMapping("/createFull")
    public License createFullLicense(@RequestParam Long userId,
                                     @RequestParam Long productId,
                                     @RequestParam Long licenseTypeId,
                                     @RequestParam int deviceCount,
                                     @RequestParam(required = false) String description) {
        return licenseService.createFullLicense(userId, productId, licenseTypeId, deviceCount, description);
    }

    // Активация лицензии
    @PostMapping("/activate")
    public Ticket activateLicense(@RequestParam String code,
                                  @RequestParam Long userId,
                                  @RequestParam String deviceName,
                                  @RequestParam String macAddress) {
        return licenseService.activateLicense(code, userId, deviceName, macAddress);
    }

    // Проверка (валидация) лицензии
    @GetMapping("/check")
    public Ticket checkLicense(@RequestParam String code, @RequestParam String macAddress) {
        return licenseService.checkLicense(code, macAddress);
    }

    // Продление лицензии
    @PostMapping("/renew")
    public Ticket renewLicense(@RequestParam String code,
                               @RequestParam Long userId,
                               @RequestParam int additionalDays) {
        return licenseService.renewLicense(code, userId, additionalDays);
    }
}
