package com.example.antivirusbackend.service;

import com.example.antivirusbackend.entity.Device;
import com.example.antivirusbackend.entity.User;
import com.example.antivirusbackend.repository.DeviceRepository;
import com.example.antivirusbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;

    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
    }

    public Device createDevice(String name, String macAddress, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        Device device = new Device();
        device.setName(name);
        device.setMacAddress(macAddress);
        device.setUser(user);
        return deviceRepository.save(device);
    }

    public Device getById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }

    public List<Device> getAll() {
        return deviceRepository.findAll();
    }

    public Device updateDevice(Long id, String name, String macAddress, Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;
        return deviceRepository.findById(id).map(d -> {
            d.setName(name);
            d.setMacAddress(macAddress);
            d.setUser(user);
            return deviceRepository.save(d);
        }).orElse(null);
    }

    public boolean deleteDevice(Long id) {
        if (deviceRepository.existsById(id)) {
            deviceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
