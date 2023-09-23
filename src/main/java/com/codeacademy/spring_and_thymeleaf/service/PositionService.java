package com.codeacademy.spring_and_thymeleaf.service;

import com.codeacademy.spring_and_thymeleaf.model.Device;
import com.codeacademy.spring_and_thymeleaf.model.Position;
import com.codeacademy.spring_and_thymeleaf.model.dto.PositionDTO;
import com.codeacademy.spring_and_thymeleaf.repository.PositionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position addPostion(Position position) {
        return positionRepository.save(position);
    }

    public Page<Position> findPaginated(Device device, Pageable pageable) {
        return positionRepository.findByDevice(device, pageable);
    }

    public PositionDTO findLastPosition(Device device) {
        Position position = positionRepository.findFirstByDeviceOrderByDateDesc(device);
        if(position == null)
            return null;
        PositionDTO positionDTO = new PositionDTO();
        positionDTO.setDeviceId(position.getDevice().getDeviceId());
        positionDTO.setDate(position.getDate());
        positionDTO.setLatitude(position.getLatitude());
        positionDTO.setLongitude(position.getLongitude());
        positionDTO.setSpeed(position.getSpeed());
        return positionDTO;
    }

}
