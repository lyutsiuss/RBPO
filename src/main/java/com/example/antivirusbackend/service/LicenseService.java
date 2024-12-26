package com.example.antivirusbackend.service;

import com.example.antivirusbackend.dto.Ticket;
import com.example.antivirusbackend.entity.*;
import com.example.antivirusbackend.repository.LicenseRepository;
import com.example.antivirusbackend.repository.ProductRepository;
import com.example.antivirusbackend.repository.UserRepository;
import com.example.antivirusbackend.repository.LicenseTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LicenseService {
    private final LicenseRepository licenseRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final LicenseTypeRepository licenseTypeRepository;
    private final DeviceLicenseService deviceLicenseService;
    private final DeviceService deviceService;
    private final LicenseHistoryService licenseHistoryService;

    public LicenseService(LicenseRepository licenseRepository,
                          UserRepository userRepository,
                          ProductRepository productRepository,
                          LicenseTypeRepository licenseTypeRepository,
                          DeviceLicenseService deviceLicenseService,
                          DeviceService deviceService,
                          LicenseHistoryService licenseHistoryService) {
        this.licenseRepository = licenseRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.licenseTypeRepository = licenseTypeRepository;
        this.deviceLicenseService = deviceLicenseService;
        this.deviceService = deviceService;
        this.licenseHistoryService = licenseHistoryService;
    }

    public List<License> getAll() {
        return licenseRepository.findAll();
    }

    public License getById(Long id) {
        return licenseRepository.findById(id).orElse(null);
    }

    // Метод создания упрощённой лицензии
    public License createLicense(String code, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        License license = new License();
        license.setCode(code);
        license.setOwner(user); // Устанавливаем owner
        license.setUser(user);  // Устанавливаем user
        return licenseRepository.save(license);
    }

    // Метод создания полноценной лицензии
    public License createFullLicense(Long userId, Long productId, Long licenseTypeId, int deviceCount, String description) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found");

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) throw new RuntimeException("Product not found");

        LicenseType licenseType = licenseTypeRepository.findById(licenseTypeId).orElse(null);
        if (licenseType == null) throw new RuntimeException("LicenseType not found");

        License license = new License();
        license.setOwner(user); // Устанавливаем owner
        license.setUser(user);  // Устанавливаем user
        license.setProduct(product);
        license.setLicenseType(licenseType);
        license.setCode(UUID.randomUUID().toString()); // Генерируем уникальный код
        license.setDuration(licenseType.getDefaultDuration());
        // Не устанавливаем firstActivationDate и endingDate здесь
        license.setBlocked(false);
        license.setDeviceCount(deviceCount);
        license.setDescription(description);
        License saved = licenseRepository.save(license);

        // Записываем историю
        licenseHistoryService.createHistory(
                saved.getId(),
                userId,
                "CREATED",
                "License created"
        );

        return saved;
    }

    public License updateLicense(Long id, String newCode) {
        return licenseRepository.findById(id).map(l -> {
            l.setCode(newCode);
            return licenseRepository.save(l);
        }).orElse(null);
    }

    public boolean deleteLicense(Long id) {
        if (licenseRepository.existsById(id)) {
            licenseRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Активация лицензии
    public Ticket activateLicense(String licenseCode, Long userId, String deviceName, String macAddress) {
        License license = licenseRepository.findByCode(licenseCode)
                .orElseThrow(() -> new RuntimeException("License not found"));

        if (license.isBlocked()) throw new RuntimeException("License is blocked");
        if (license.getEndingDate() != null && license.getEndingDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("License is expired");

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new RuntimeException("User not found");

        // Проверим сколько устройств уже привязано к этой лицензии
        List<DeviceLicense> dlList = deviceLicenseService.getByLicense(license);
        if (dlList.size() >= license.getDeviceCount()) {
            throw new RuntimeException("No available device slots for this license");
        }

        // Создадим устройство
        Device device = deviceService.createDevice(deviceName, macAddress, user.getId());
        if (device == null) throw new RuntimeException("Can't create device");

        // Привяжем устройство к лицензии
        DeviceLicense dl = deviceLicenseService.createDeviceLicense(license.getId(), device.getId());
        if (dl == null) throw new RuntimeException("Failed to attach device to license");

        // Установим firstActivationDate, если он не установлен
        boolean firstActivation = false;
        if (license.getFirstActivationDate() == null) {
            license.setFirstActivationDate(LocalDateTime.now());
            firstActivation = true;
        }

        // Рассчитаем endingDate на основе licenseType.defaultDuration
        license.setEndingDate(LocalDateTime.now().plusDays(license.getLicenseType().getDefaultDuration()));
        licenseRepository.save(license);

        // Запишем в историю
        String historyDescription = firstActivation ? "License activated for the first time on device " + device.getId()
                : "License activated on device " + device.getId();

        licenseHistoryService.createHistory(
                license.getId(),
                userId,
                "ACTIVATED",
                historyDescription
        );

        // Вернём Ticket
        return buildTicket(license, device);
    }

    // Проверка лицензии
    public Ticket checkLicense(String licenseCode, String macAddress) {
        License license = licenseRepository.findByCode(licenseCode)
                .orElseThrow(() -> new RuntimeException("License not found"));

        // Найдём DeviceLicense по лицензии и macAddress
        List<DeviceLicense> dlList = deviceLicenseService.getByLicense(license);
        DeviceLicense found = null;
        for (DeviceLicense dl : dlList) {
            if (dl.getDevice().getMacAddress().equalsIgnoreCase(macAddress)) {
                found = dl;
                break;
            }
        }

        if (found == null) throw new RuntimeException("This device is not linked to the license");

        if (license.isBlocked()) throw new RuntimeException("License is blocked");
        if (license.getEndingDate() != null && license.getEndingDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("License is expired");

        return buildTicket(license, found.getDevice());
    }

    // Продление лицензии
    public Ticket renewLicense(String licenseCode, Long userId, int additionalDays) {
        License license = licenseRepository.findByCode(licenseCode)
                .orElseThrow(() -> new RuntimeException("License not found"));

        // Только владелец может продлевать
        if (!license.getOwner().getId().equals(userId)) {
            throw new RuntimeException("Only license owner can renew");
        }

        if (license.isBlocked()) throw new RuntimeException("Can't renew blocked license");

        // Продлим
        if (license.getEndingDate() == null) {
            throw new RuntimeException("Cannot renew a license that has not been activated");
        }
        license.setEndingDate(license.getEndingDate().plusDays(additionalDays));
        licenseRepository.save(license);

        // Запишем в историю
        licenseHistoryService.createHistory(
                license.getId(),
                userId,
                "RENEWED",
                "License renewed for " + additionalDays + " days"
        );

        // Возвращаем Ticket
        return buildTicket(license, null);
    }

    private Ticket buildTicket(License license, Device device) {
        Ticket ticket = new Ticket();
        ticket.setServerDate(LocalDateTime.now());
        ticket.setLifeTimeSeconds(300);
        ticket.setActivationDate(license.getFirstActivationDate());
        ticket.setEndingDate(license.getEndingDate());
        ticket.setUserId(license.getUser().getId()); // Устанавливаем userId из поля user
        if (device != null) {
            ticket.setDeviceId("DeviceID:" + device.getId());
        } else {
            ticket.setDeviceId("no-device-info");
        }
        ticket.setBlocked(license.isBlocked());
        ticket.setDigitalSignature("signature-placeholder");
        return ticket;
    }
}
