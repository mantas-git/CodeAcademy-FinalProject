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
    public String showIndex() {
        return "index";
    }

    @GetMapping("/monitoring")
    public String showMonitoring(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("newDevice", new Device());
        return "monitoring";
    }

//    @GetMapping("monitoring/{deviceId}")
//    public String showMonitoring(@PathVariable Long deviceId, Model model) {
//        Device device = deviceService.getDeviceByDeviceId(deviceId);
//        model.addAttribute("device", device);
//        model.addAttribute("newDevice", new Device());
//        return "monitoring";
//    }

    @GetMapping("/monitoring/run")
    public String showMonitoringByParam(@RequestParam Long deviceId, Model model) {
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        model.addAttribute("device", device);
        model.addAttribute("newDevice", new Device());
        return "monitoring";
    }

    @GetMapping("/devices")
    public String showAllDevices(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        model.addAttribute("newDevice", new Device());
        return "devices";
    }

    @GetMapping("/devices/search")
    public String showFilteredDevices(@RequestParam String searchText, Model model) {
        List<Device> devices = deviceService.getFilteredDevices(searchText);
        model.addAttribute("devices", devices);
        return "devices";
    }

    @PostMapping("/devices")
    public String postDevice(Device newDevice, Model model) {
        deviceService.addNewDevice(newDevice);
        return showAllDevices(model);
    }

    @PostMapping("/devices/delete")
    public String deleteDevice(@RequestParam Long id, Model model) {
        deviceService.deleteDevice(id);
        return showAllDevices(model);
    }
    @GetMapping("/about")
    public String runAbout() {
        return "about";
    }

}
