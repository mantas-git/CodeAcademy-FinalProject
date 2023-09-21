package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/devices")
public class DevicesController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    private final DeviceService deviceService;

    public DevicesController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public String showAllDevicesPageable(Device device, Model model,
                                         @AuthenticationPrincipal User user,
                                         @PageableDefault(size = 15)
                                         Pageable pageable) {
        logger.info("All Devices for User: {}", user);
        getAllDevices(user, pageable, model);
        return "devices";
    }

    @PostMapping("/add")
    public String addDevice(@Valid Device device,
                            BindingResult errors,
                            @AuthenticationPrincipal User user,
                            @PageableDefault(size = 15) Pageable pageable,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        if (!user.isVerified()) {
            InfoMessage infoMessage = new InfoMessage(true, "Jūsų el. paštas dar nepatvirtintas");
            redirectAttributes.addFlashAttribute("infoMessage", infoMessage);
            return "redirect:/devices";
        }
        if (errors.hasErrors()) {
            logger.info("Add Device User: {}", user);
            getAllDevices(user, pageable, model);
            logger.info("BindingResult errors: {}", errors);
            return "devices";
        }
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.addDevice(device));
        return "redirect:/devices";

    }

    @GetMapping("/search")
    public String showFilteredDevices(@RequestParam String search,
                                      @AuthenticationPrincipal User user,
                                      @PageableDefault(size = 15) Pageable pageable,
                                      Device device,
                                      Model model) {
        logger.info("Search User: {}", user);
        logger.info("Search text {}", search);
        if (!search.isEmpty()) {
            Page<Device> devices = deviceService.findFilteredDevicesPaginated(user, pageable, search);
            logger.info("Loaded Devices when searching {}: {}", search, devices);
            pageableList(model, devices);
            return "devices";
        }
        return "redirect:/devices";
    }


    @PutMapping("/update")
    public String updateDevice(Device device, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.updateDevice(device));
        return "redirect:/devices";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDevice(@PathVariable Long id) {
        logger.info("Trying delete Device with ID {}", id);
        deviceService.deleteDevice(id);
        return "redirect:/devices";
    }

    private void getAllDevices(@AuthenticationPrincipal User user, @PageableDefault(size = 15) Pageable pageable, Model model) {
        Page<Device> devices = deviceService.findPaginated(user, pageable);
        logger.info("Loaded Devices: {}", devices);
        pageableList(model, devices);
    }

    private void pageableList(Model model, Page<Device> devices) {
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        int totalPages = devices.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }
}
