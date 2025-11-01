package com.usedcars.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Double price;
    private String color;
    private Integer mileage;
    private String fuelType;
    private String transmission;
    private String status; // AVAILABLE, SOLD
    private String description;
    private String imageUrl;
    
}