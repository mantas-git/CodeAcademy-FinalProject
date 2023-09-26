package com.codeacademy.spring_and_thymeleaf.controller;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.model.dto.PositionDTO;
import com.codeacademy.spring_and_thymeleaf.service.DeviceService;
import com.codeacademy.spring_and_thymeleaf.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class PositionsController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    private final DeviceService deviceService;
    private final PositionService positionService;

    public PositionsController(DeviceService deviceService, PositionService positionService) {
        this.deviceService = deviceService;
        this.positionService = positionService;
    }

    @PostMapping("/positions/add")
    public ResponseEntity<?> addPosition(@RequestParam("deviceId") Long deviceId,
                                         @RequestParam("latitude") Double latitude,
                                         @RequestParam("longitude") Double longitude,
                                         @RequestParam("speed") Integer speed) {
        Position position = new Position();
        position.setDevice(deviceService.getDeviceByDeviceId(deviceId));
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

    @GetMapping("/positions/getLast/{deviceId}")
    public ResponseEntity<?> getLastPosition(@PathVariable Long deviceId) {
        try {
            PositionDTO positionDTO = null;
            if (deviceId != null) {
                Device device = deviceService.getDeviceByDeviceId(deviceId);
                if(device != null)
                    positionDTO = positionService.findLastPosition(device);
            }
            if (positionDTO == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(positionDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
