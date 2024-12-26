package com.example.antivirusbackend.controller;

import com.example.antivirusbackend.entity.Device;
import com.example.antivirusbackend.service.DeviceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService ds) {
        this.deviceService = ds;
    }

    @PostMapping
    public Device createDevice(@RequestParam String name, @RequestParam String macAddress, @RequestParam Long userId) {
        return deviceService.createDevice(name, macAddress, userId);
    }

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAll();
    }

    @GetMapping("/{id}")
    public Device getDevice(@PathVariable Long id) {
        return deviceService.getById(id);
    }

    @PutMapping("/{id}")
    public Device updateDevice(@PathVariable Long id,
                               @RequestParam String name,
                               @RequestParam String macAddress,
                               @RequestParam Long userId) {
        return deviceService.updateDevice(id, name, macAddress, userId);
    }

    @DeleteMapping("/{id}")
    public String deleteDevice(@PathVariable Long id) {
        boolean deleted = deviceService.deleteDevice(id);
        return deleted ? "Device deleted" : "Device not found";
    }
}
