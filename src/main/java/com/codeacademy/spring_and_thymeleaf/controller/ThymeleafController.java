package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.dao.DeviceDao;
import com.codeacademy.spring_and_thymeleaf.model.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ThymeleafController {

    private final DeviceDao deviceDao;

    public ThymeleafController(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @GetMapping
    public String runIndex() {
        return "index";
    }

    @GetMapping("/monitoring")
    public String runMonitoring() {
        return "monitoring";
    }

    @GetMapping("/devices-dao")
    public String runDevices(Model model) {
        List<Device> devices = deviceDao.getAllTopics();
        model.addAttribute("devices", devices);
        return "devices";
    }

    @GetMapping("/about")
    public String runAbout() {
        return "about";
    }
}
