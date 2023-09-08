package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import com.codeacademy.spring_and_thymeleaf.service.PositionService;
//import jakarta.validation.Valid;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping
public class ThymeleafController {

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    private final DeviceService deviceService;
    private  final PositionService positionService;

    public ThymeleafController(DeviceService deviceService, PositionService positionService) {
        this.deviceService = deviceService;
        this.positionService = positionService;
    }

    @GetMapping
    public String showIndex(Model model) {
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "index";
    }

    @GetMapping("/monitoring")
    public String showMonitoring(Model model) {
        model.addAttribute("device", new Device());
        model.addAttribute("newDevice", new Device());
        model.addAttribute("positions", null);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "monitoring";
    }


    @GetMapping("/monitoring/run")
    public String listTopics(@RequestParam Long deviceId, Model model,
                             @PageableDefault(sort = { "date"}, direction = Sort.Direction.DESC, size = 15)
                             Pageable pageable)
    {
        Device device = deviceService.getDeviceByDeviceId(deviceId);
        Page<Position> positions = positionService.findPaginated(device, pageable);

        model.addAttribute("device", device);
        model.addAttribute("newDevice", new Device());
        model.addAttribute("positions", positions);

        int totalPages = positions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages-1)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("locale", LocaleContextHolder.getLocale());

        return "monitoring";
    }

    @GetMapping("/devices")
    public String showAllDevices(Device device, Model model) {
        List<Device> devices = deviceService.getAllDevices();
        logger.info("Loaded Devices: {}", devices);
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "devices";
    }

    @GetMapping("/devices/search")
    public String showFilteredDevices(@RequestParam String searchText, Model model) {
        List<Device> devices = deviceService.getFilteredDevices(searchText);
        model.addAttribute("devices", devices);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "devices";
    }


    @PostMapping("/devices")
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

//    @PostMapping("/devices/add")
//    public String addDevice(Device device, RedirectAttributes redirectAttributes) {
//        redirectAttributes.addFlashAttribute("infoMessage", deviceService.addDevice(device));
//        return "redirect:/devices";
//    }

    @PostMapping("/devices/update")
    public String updateDevice(Device device, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("infoMessage", deviceService.updateDevice(device));
        return "redirect:/devices";
    }

    @PostMapping("/devices/delete")
    public String deleteDevice(@RequestParam Long id) {
        deviceService.deleteDevice(id);
        return "redirect:/devices";
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
        positionService.addPostion(position);
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("error", false);
        responseMap.put("message", "Position created successfully");
        return ResponseEntity.ok(responseMap);
    }

}
