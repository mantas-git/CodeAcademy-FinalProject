package com.codeacademy.spring_and_thymeleaf.repository;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByCommentContainingIgnoreCaseOrVnrContainingIgnoreCaseOrderByVnrAsc(String searchText, String searchText1);
}
