package com.usedcars.booking.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    private String customerName;
    private String VehicleModel;
    private String serviceType;
    private String bookingDate;
    private String contactNumber;
}
