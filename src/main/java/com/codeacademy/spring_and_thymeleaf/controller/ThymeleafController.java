package com.codeacademy.spring_and_thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ThymeleafController {

    @GetMapping
    public String runIndex() {
        return "index";
    }

    @GetMapping("/monitoring")
    public String runMonitoring() {
        return "monitoring";
    }

    @GetMapping("/devices")
    public String runDevices() {
        return "devices";
    }

    @GetMapping("/about")
    public String runAbout() {
        return "about";
    }
}
