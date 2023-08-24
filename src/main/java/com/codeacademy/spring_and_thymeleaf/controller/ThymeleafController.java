package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ThymeleafController {

    private final DeviceService deviceService;

    public ThymeleafController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String runIndex() {
        return "index";
    }

    @GetMapping("/monitoring")
    public String runMonitoring() {
        return "monitoring";
    }

    @GetMapping("monitoring/{deviceId}")
    public String showMonitoring(@PathVariable Long deviceId, Model model) {
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        model.addAttribute("device", device);
        return "monitoring";
    }

    @GetMapping("/devices")
    public String showAllDevices(Model model, Model modelNew) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        modelNew.addAttribute("newDevice", new Device());
        return "devices";
    }

    @GetMapping("/devices/search")
    public String showFilteredDevices(@RequestParam String searchText, Model model) {
        List<Device> devices = deviceService.getFilteredDevices(searchText);
        model.addAttribute("devices", devices);
        return "devices";
    }

    @PostMapping("/devices")
    public String postDevice(Device newDevice, Model model, Model modelNew) {
        deviceService.addNewDevice(newDevice);
        return showAllDevices(model, modelNew);
    }

    @DeleteMapping("/devices/delete/{id}")
    public String deleteDevice(@PathVariable Long id, Model model, Model modelNew) {
        deviceService.deleteDevice(id);
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        modelNew.addAttribute("newDevice", new Device());
        return "devices";
    }
    @GetMapping("/about")
    public String runAbout() {
        return "about";
    }

}
