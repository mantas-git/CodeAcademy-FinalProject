package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.repository.DeviceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    public InfoMessage addDevice(Device device) {
        InfoMessage infoMessage = new InfoMessage();
        if(deviceRepository.existsDeviceByDeviceId(device.getDeviceId())) {
            infoMessage.setError(true);
            infoMessage.setMessageText("Įrenginys su tokiu įrenginio ID jau egzistuoja.\nPridėjimas negalimas.");
        }
        else {
            device.setCreateDate(LocalDate.now());
            device.setUserId(0);
            deviceRepository.save(device);
            infoMessage.setError(false);
            infoMessage.setMessageText("Pridėtas naujas įrenginys");
        }
        System.out.println(infoMessage);
        return infoMessage;

    }

    public Device getDevice(Long id) {
        return deviceRepository.findById(id).get();
    }

    public List<Device> getFilteredDevices(String searchText) {
        return deviceRepository.findByCommentContainingIgnoreCaseOrTransportNrContainingIgnoreCaseOrderByTransportNrAsc(searchText, searchText);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public Device getDeviceByDeviceId(Long deviceId) {
        return deviceRepository.findByDeviceId(deviceId).get(0);
    }

    public InfoMessage updateDevice(Device device) {
        InfoMessage infoMessage = new InfoMessage();
        if(deviceRepository.existsDeviceByDeviceId(device.getDeviceId())) {
            infoMessage.setError(true);
            infoMessage.setMessageText("Įrenginys su tokiu įrenginio ID jau egzistuoja.\nAtnaujinimas negalimas.");

        }
        else {
            deviceRepository.save(device);
            infoMessage.setError(false);
            infoMessage.setMessageText("Įrenginys atnaujintas");
        }
        return infoMessage;
    }
}
