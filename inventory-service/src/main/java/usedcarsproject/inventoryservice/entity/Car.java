package usedcarsproject.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import usedcarsproject.inventoryservice.util.CarStatus;

@Entity
@Getter
@Setter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String make;
    private String model;
    @Column(name = "car_year")
    private Integer year;
    private Integer mileage;
    private BigDecimal price;
    private String description;
    private LocalDate dateListed;

    @Enumerated(EnumType.STRING)
    private CarStatus carStatus;
}
