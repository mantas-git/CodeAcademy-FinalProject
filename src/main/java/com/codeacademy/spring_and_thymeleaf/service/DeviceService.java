package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {
    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device addNewDevice(Device newDevice) {
        newDevice.setCreateDate(LocalDate.now());
        newDevice.setUserId(0);
        return deviceRepository.save(newDevice);
    }

    public Device getDevice(Long id) {
        return deviceRepository.findAllById(Collections.singleton(id)).get(0);
    }

    public List<Device> getFilteredDevices(String searchText) {
        return deviceRepository.findByCommentContainingIgnoreCaseOrTransportNrContainingIgnoreCaseOrderByTransportNrAsc(searchText, searchText);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }
}
