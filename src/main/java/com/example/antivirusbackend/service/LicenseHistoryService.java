package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.License;
import com.example.antivirusbackend.entity.LicenseHistory;
import com.example.antivirusbackend.entity.User;
import com.example.antivirusbackend.repository.LicenseHistoryRepository;
import com.example.antivirusbackend.repository.LicenseRepository;
import com.example.antivirusbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LicenseHistoryService {
    private final LicenseHistoryRepository licenseHistoryRepository;
    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;

    public LicenseHistoryService(LicenseHistoryRepository lhr, LicenseRepository lr, UserRepository ur) {
        this.licenseHistoryRepository = lhr;
        this.licenseRepository = lr;
        this.userRepository = ur;
    }

    public LicenseHistory createHistory(Long licenseId, Long userId, String status, String description) {
        License license = licenseRepository.findById(licenseId).orElse(null);
        if (license == null) throw new RuntimeException("License not found");

        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        LicenseHistory lh = new LicenseHistory();
        lh.setLicense(license);
        lh.setUser(user);
        lh.setStatus(status);
        lh.setDescription(description);
        lh.setChangeDate(LocalDateTime.now());
        return licenseHistoryRepository.save(lh);
    }

    public List<LicenseHistory> getAll() {
        return licenseHistoryRepository.findAll();
    }

    public LicenseHistory getById(Long id) {
        return licenseHistoryRepository.findById(id).orElse(null);
    }

    public boolean deleteHistory(Long id) {
        if (licenseHistoryRepository.existsById(id)) {
            licenseHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
