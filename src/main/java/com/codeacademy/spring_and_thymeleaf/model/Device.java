package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="devices")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    @DecimalMin(value = "1000", message = "'Device ID' negali būti < 1000")
    private Long deviceId;
    @NotBlank(message = "transporto nr tuscias")
    private String transportNr;
    private String comment;
    private LocalDate createDate;
    private Integer userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", orphanRemoval = true)
    private List<Position> positions = new ArrayList<>();
}
