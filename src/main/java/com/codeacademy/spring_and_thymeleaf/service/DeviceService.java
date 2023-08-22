package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.repository.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
        return deviceRepository.save(newDevice);
    }

    public List<Device> getFilteredDevices(String searchText) {
        List<Device> devices = new ArrayList<Device>();
        return deviceRepository.findByCommentContainingIgnoreCaseOrVnrContainingIgnoreCaseOrderByVnrAsc(searchText, searchText);

    }
}
