package com.usedcars.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    
    private Long id;
    private Long userId;
    private Long carId;
    private String serviceType; // MAINTENANCE, REPAIR, INSPECTION
    private LocalDateTime bookingDate;
    private String status; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    private String notes;
    private String customerName;
    private String customerEmail;
    private String carDetails;
    
}