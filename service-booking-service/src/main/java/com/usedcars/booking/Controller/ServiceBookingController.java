package com.usedcars.booking.Controller;

import com.usedcars.booking.Entity.ServiceBooking;
import com.usedcars.booking.Service.ServiceBookingService;
import com.usedcars.booking.dto.BookingDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class ServiceBookingController {

    private final ServiceBookingService bookingService;

    public ServiceBookingController(ServiceBookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<ServiceBooking> createBooking(@RequestBody BookingDto bookingDto) {
        ServiceBooking booking = new ServiceBooking();
        booking.setCustomerName(bookingDto.getCustomerName());
        booking.setVehicleModel(bookingDto.getVehicleModel());
        booking.setServiceType(bookingDto.getServiceType());
        booking.setBookingDate(LocalDate.parse(bookingDto.getBookingDate()));
        booking.setContactNumber(bookingDto.getContactNumber());
        booking.setStatus("Pending");

        ServiceBooking savedBooking = bookingService.createBooking(booking);
        return ResponseEntity.ok(savedBooking);
    }
    @GetMapping
    public List<ServiceBooking> getAllBookings(){
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Optional<ServiceBooking> getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/status")
    public ResponseEntity<ServiceBooking> updateBookingStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestBody) {

        String newStatus = requestBody.get("status");
        Optional<ServiceBooking> optionalBooking = bookingService.getBookingById(id);

        if (optionalBooking.isPresent()) {
            ServiceBooking booking = optionalBooking.get();
            booking.setStatus(newStatus);
            ServiceBooking updatedBooking = bookingService.createBooking(booking);
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
