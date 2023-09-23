package com.codeacademy.spring_and_thymeleaf.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@Data
public class PositionDTO {
    private Long deviceId;
    private LocalDateTime date;
    private Double latitude;
    private Double longitude;
    private Integer speed;
}
