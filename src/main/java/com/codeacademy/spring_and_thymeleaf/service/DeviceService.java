package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.InfoMessage;
import com.codeacademy.spring_and_thymeleaf.model.Role;
import com.codeacademy.spring_and_thymeleaf.model.User;
import com.codeacademy.spring_and_thymeleaf.repository.DeviceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class DeviceService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceService.class);
    private DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public InfoMessage addDevice(Device device) {
        InfoMessage infoMessage = new InfoMessage();
        if (deviceRepository.existsDeviceByDeviceId(device.getDeviceId())) {
            infoMessage.setError(true);
            infoMessage.setMessageText("Įrenginys su tokiu įrenginio ID jau egzistuoja.\nPridėjimas negalimas.");
        } else {
            device.setCreateDate(LocalDate.now());
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
        logger.info("Device update. Data for device update: {}", device);
        InfoMessage infoMessage = new InfoMessage();
        if (!device.getId().equals(deviceRepository.findByDeviceId(device.getDeviceId()).get(0).getId())) {
            infoMessage.setError(true);
            infoMessage.setMessageText("Įrenginys su tokiu įrenginio ID jau egzistuoja.\nAtnaujinimas negalimas.");
        } else {
            Device existingDevice = getDevice(device.getId());
            existingDevice.setDeviceId(device.getDeviceId());
            existingDevice.setTransportNr(device.getTransportNr());
            existingDevice.setComment(device.getComment());
            existingDevice.setCreateDate(device.getCreateDate());
            deviceRepository.save(existingDevice);
            infoMessage.setError(false);
            infoMessage.setMessageText("Įrenginys atnaujintas");
        }
        return infoMessage;
    }

    public List<Device> getAllDevicesByUser(User user) {
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            if (role.toString().equals("ADMIN"))
                return getAllDevices();
        }
        return deviceRepository.findByUserId(user.getId());
    }
}
