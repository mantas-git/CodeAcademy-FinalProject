package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/devices")
public class DevicesController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    private final DeviceService deviceService;

    public DevicesController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

//      @GetMapping("/devices")
//    public String showAllDevices(Device device, Model model) {
//        List<Device> devices = deviceService.getAllDevices();
//        logger.info("Loaded Devices: {}", devices);
//        model.addAttribute("devices", devices);
//        model.addAttribute("locale", LocaleContextHolder.getLocale());
//        return "devices";
//    }

    @GetMapping
    public String showAllUsersDevices(Device device, Model model, @AuthenticationPrincipal User user) {
        logger.info("User: {}", user);
        List<Device> devices = deviceService.getAllDevicesByUser(user);
        logger.info("Loaded Devices: {}", devices);
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "devices";
    }

    @PostMapping("/add")
    public String addDevice(@Valid Device device,
                            BindingResult errors,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (errors.hasErrors()) {
            List<Device> devices = deviceService.getAllDevices();
            model.addAttribute("devices", devices);
            model.addAttribute("locale", LocaleContextHolder.getLocale());
            logger.info("BindingResult errors: {}", errors);
            return "devices";
        }
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.addDevice(device));
        return "redirect:/devices";
    }

    @GetMapping("/search")
    public String showFilteredDevices(@RequestParam String search, Device device, Model model) {
        List<Device> devices = deviceService.getFilteredDevices(search);
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "devices";
    }


//    @PostMapping("/devices/add")
//    public String addDevice(Device device, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("infoMessage", deviceService.addDevice(device));
//        return "redirect:/devices";
//    }

    @PutMapping("/update")
    public String updateDevice(Device device, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.updateDevice(device));
        return "redirect:/devices";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id) {
        logger.info(">>>>> Trying delete Device with ID {}", id);
        deviceService.deleteDevice(id);
        return "redirect:/devices";
    }

}
