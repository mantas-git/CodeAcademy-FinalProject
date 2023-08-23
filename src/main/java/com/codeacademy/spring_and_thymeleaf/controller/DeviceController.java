package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
//        List<Device> devices = deviceService.getAllDevices();
//        model.addAttribute("devices", devices);
//        modelNew.addAttribute("newDevice", new Device());
        return showAllDevices(model, modelNew);
    }
}
