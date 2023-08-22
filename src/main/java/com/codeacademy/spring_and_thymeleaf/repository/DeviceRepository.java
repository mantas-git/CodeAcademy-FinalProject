package com.codeacademy.spring_and_thymeleaf.repository;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
