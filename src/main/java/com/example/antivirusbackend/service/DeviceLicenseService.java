package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.DeviceLicense;
import com.example.antivirusbackend.entity.License;
import com.example.antivirusbackend.repository.DeviceLicenseRepository;
import com.example.antivirusbackend.repository.DeviceRepository;
import com.example.antivirusbackend.repository.LicenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeviceLicenseService {
    private final DeviceLicenseRepository deviceLicenseRepository;
    private final LicenseRepository licenseRepository;
    private final DeviceRepository deviceRepository;

    public DeviceLicenseService(DeviceLicenseRepository dlRepo, LicenseRepository lRepo, DeviceRepository dRepo) {
        this.deviceLicenseRepository = dlRepo;
        this.licenseRepository = lRepo;
        this.deviceRepository = dRepo;
    }

    public DeviceLicense createDeviceLicense(Long licenseId, Long deviceId) {
        License license = licenseRepository.findById(licenseId).orElse(null);
        if (license == null) return null;
        var device = deviceRepository.findById(deviceId).orElse(null);
        if (device == null) return null;

        DeviceLicense dl = new DeviceLicense();
        dl.setLicense(license);
        dl.setDevice(device);
        dl.setActivationDate(LocalDateTime.now());
        return deviceLicenseRepository.save(dl);
    }

    public List<DeviceLicense> getAll() {
        return deviceLicenseRepository.findAll();
    }

    public DeviceLicense getById(Long id) {
        return deviceLicenseRepository.findById(id).orElse(null);
    }

    public boolean deleteDeviceLicense(Long id) {
        if (deviceLicenseRepository.existsById(id)) {
            deviceLicenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<DeviceLicense> getByLicense(License license) {
        return deviceLicenseRepository.findByLicense(license);
    }
}
