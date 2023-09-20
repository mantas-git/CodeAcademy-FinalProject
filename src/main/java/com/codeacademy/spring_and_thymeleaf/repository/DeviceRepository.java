package com.codeacademy.spring_and_thymeleaf.repository;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long>, PagingAndSortingRepository<Device, Long> {
    List<Device> findByCommentContainingIgnoreCaseOrTransportNrContainingIgnoreCaseOrderByTransportNrAsc(String searchText, String searchText1);

    List<Device> findByDeviceId(Long deviceId);

    boolean existsDeviceByDeviceId(Long deviceId);

//    List<Device> findByUserId(Long id);

    Page<Device> findByUserId(Long id, Pageable pageable);

    List<Device> findByDeviceIdAndUserId(Long deviceId, Long userId);

    Page<Device> findByCommentContainingIgnoreCaseOrTransportNrContainingIgnoreCaseOrderByTransportNrAsc(String searchText, String searchText1, Pageable pageable);

    Page<Device> findByCommentContainingIgnoreCaseOrTransportNrContainingIgnoreCaseAndUserIdOrderByTransportNrAsc(String searchText, String searchText1, Long id, Pageable pageable);
}
