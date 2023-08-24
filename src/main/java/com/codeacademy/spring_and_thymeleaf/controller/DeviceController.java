package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String showAllDevices(Model model, Model modelNew) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        modelNew.addAttribute("newDevice", new Device());
        return "devices";
    }

    @GetMapping("/search")
    public String showFilteredDevices(@RequestParam String searchText, Model model) {
        List<Device> devices = deviceService.getFilteredDevices(searchText);
        model.addAttribute("devices", devices);
        return "devices";
    }

    @PostMapping
    public String postDevice(Device newDevice, Model model, Model modelNew) {
        deviceService.addNewDevice(newDevice);
        return showAllDevices(model, modelNew);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id, Model model, Model modelNew) {
        deviceService.deleteDevice(id);
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        modelNew.addAttribute("newDevice", new Device());
        return "devices";
    }

    @GetMapping("monitoring/{id}")
    public String showMonitoring(@PathVariable Long id, Model model) {
        Device device = deviceService.getDevice(id);
        model.addAttribute("device", device);
        return "monitoring";
    }
}
