package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import com.codeacademy.spring_and_thymeleaf.service.PositionService;
import com.codeacademy.spring_and_thymeleaf.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    private final UserService userService;

    public ThymeleafController(DeviceService deviceService, PositionService positionService, UserService userService) {
        this.deviceService = deviceService;
        this.positionService = positionService;
        this.userService = userService;
    }

    @GetMapping("/")
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
        model.addAttribute("newDevice", new Device()); //?
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

    @GetMapping("/users")
    public String showUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("locale", LocaleContextHolder.getLocale());
        return "users";
    }

    @DeleteMapping("/users/delete/{id}")
    public String detelteUser(@PathVariable Long id) {
        logger.info(">>>>> Trying delete User with ID {}", id);
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
