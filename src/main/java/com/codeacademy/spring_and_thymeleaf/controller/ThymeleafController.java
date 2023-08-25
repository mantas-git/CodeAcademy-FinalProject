package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    @PostMapping("/monitoring")
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
    public String addDevice(Device device, Model model) {
        deviceService.addDevice(device);
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

    @PostMapping("/positions/add")
    public ResponseEntity<?> addPosition(@RequestParam("deviceId") Long deviceId,
                                         @RequestParam("latitude") Double latitude,
                                         @RequestParam("longitude") Double longitude,
                                         @RequestParam("speed") Integer speed) {
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        Position position = new Position();
        position.setDevice(device);
        position.setDate(LocalDateTime.now());
        position.setLatitude(latitude);
        position.setLongitude(longitude);
        position.setSpeed(speed);
        deviceService.addPosition(position, device);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", false);
        responseMap.put("message", "Position created successfully");
        return ResponseEntity.ok(responseMap);
    }

}
