package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.DeviceLicense;
import com.example.antivirusbackend.service.DeviceLicenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devicelicense")
public class DeviceLicenseController {
    private final DeviceLicenseService deviceLicenseService;

    public DeviceLicenseController(DeviceLicenseService dls) {
        this.deviceLicenseService = dls;
    }

    @PostMapping
    public DeviceLicense createDeviceLicense(@RequestParam Long licenseId, @RequestParam Long deviceId) {
        return deviceLicenseService.createDeviceLicense(licenseId, deviceId);
    }

    @GetMapping
    public List<DeviceLicense> getAll() {
        return deviceLicenseService.getAll();
    }

    @GetMapping("/{id}")
    public DeviceLicense getById(@PathVariable Long id) {
        return deviceLicenseService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean deleted = deviceLicenseService.deleteDeviceLicense(id);
        return deleted ? "DeviceLicense deleted" : "Not found";
    }
}
