package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.LicenseHistory;
import com.example.antivirusbackend.service.LicenseHistoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/licensehistory")
public class LicenseHistoryController {
    private final LicenseHistoryService licenseHistoryService;

    public LicenseHistoryController(LicenseHistoryService lhs) {
        this.licenseHistoryService = lhs;
    }

    @PostMapping
    public LicenseHistory createHistory(@RequestParam Long licenseId,
                                        @RequestParam(required = false) Long userId,
                                        @RequestParam String status,
                                        @RequestParam String description) {
        return licenseHistoryService.createHistory(licenseId, userId, status, description);
    }

    @GetMapping
    public List<LicenseHistory> getAll() {
        return licenseHistoryService.getAll();
    }

    @GetMapping("/{id}")
    public LicenseHistory getById(@PathVariable Long id) {
        return licenseHistoryService.getById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boolean deleted = licenseHistoryService.deleteHistory(id);
        return deleted ? "History deleted" : "Not found";
    }
}
