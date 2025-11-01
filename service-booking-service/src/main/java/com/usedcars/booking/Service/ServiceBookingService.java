package com.usedcars.booking.Service;

import com.usedcars.booking.Entity.ServiceBooking;
import com.usedcars.booking.repository.ServiceBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceBookingService {

    @Autowired
    private final ServiceBookingRepository bookingRepository;

    public ServiceBookingService(ServiceBookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public ServiceBooking createBooking(ServiceBooking booking) {
        if (booking.getId() == 0) {
            booking.setStatus("Pending");
        }
        return bookingRepository.save(booking);
    }

    public List<ServiceBooking> getAllBookings(){
        return bookingRepository.findAll();
    }

    public Optional<ServiceBooking> getBookingById(Long id){
        return bookingRepository.findById(id);
    }

    public void deleteBooking(Long id){
        bookingRepository.deleteById(id);
    }
}
