package com.codeacademy.spring_and_thymeleaf.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    @DecimalMin(value="1000", message="{tooShortDevId}")
    @NotNull(message = "{canNotBeEmpty}")
    @Column(unique=true)
    private Long deviceId;
    private String transportNr;
    private String comment;
    private LocalDate createDate;
    private Integer userId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "device", orphanRemoval = true)
    private List<Position> positions = new ArrayList<>();
}
