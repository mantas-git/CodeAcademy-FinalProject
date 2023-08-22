package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String runDevices(Model model) {
        List<Device> devices = deviceService.getAllDevices();
        model.addAttribute("devices", devices);
        return "devices";
    }

//    @GetMapping
//    public String getTopics(Model model) {
//
////        List<Topic> topics = List.of(
////                new Topic("Most popular films", "More info"),
////                new Topic("Top rated films", "More info"),
////                new Topic("Latest movies", "More info"),
////                new Topic("Most expensive movies", "More info")
////        );
//
//        List<Device> topics = deviceService.getAllDevices();
//
//        model.addAttribute("topics", topics);
//        model.addAttribute("newTopic", new Device());
//
//        return "topics";
//    }

    @PostMapping
    public String postTopics(Device newDevice, Model model) {

        System.out.println(newDevice);

        Device savedDevice = deviceService.addNewDevice(newDevice);
        model.addAttribute("newTopic", savedDevice);
        return "topic";
    }
}
