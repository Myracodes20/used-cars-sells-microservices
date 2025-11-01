package com.usedcars.webapp.controller;

import com.usedcars.webapp.dto.BookingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingPageController {

    @Autowired
    private WebClient __bookingWebClient__;

    /**
     * Show service booking form
     */
    @GetMapping("/new")
    public String bookingForm(@RequestParam(required = false) Long carId, Model model) {
        BookingDto booking = new BookingDto();
        booking.setCarId(carId);

        model.addAttribute("title", "Book a Service");
        model.addAttribute("booking", booking);
        return "service/booking-form";
    }

    /**
     * Handle booking form submission
     */
    @PostMapping("/save")
    public String createBooking(@ModelAttribute BookingDto bookingDto, Model model) {
        // TODO: Call __backend__ API to create booking
        bookingDto.setBookingDate(LocalDateTime.now());
        bookingDto.setStatus("PENDING");

        System.out.println("Creating booking: " + bookingDto);

        return "redirect:/service/my-bookings";
    }

    /**
     * Show user's bookings
     */
    @GetMapping("/my-bookings")
    public String myBookings(Model model) {
        // TODO: Get logged-in user's bookings from API
        // For now, using mock data
        List<BookingDto> bookings = getMockBookings();

        model.addAttribute("title", "My Bookings");
        model.addAttribute("bookings", bookings);
        return "service/my-bookings";
    }

    /**
     * Cancel a booking
     */
    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id) {
        // TODO: Call __backend__ API to cancel booking
        System.out.println("Cancelling booking: " + id);

        return "redirect:/servicec/my-bookings";
    }

    // ==================== MOCK DATA ====================

    private List<BookingDto> getMockBookings() {
        List<BookingDto> bookings = new ArrayList<>();

        bookings.add(new BookingDto(1L, 1L, 1L, "MAINTENANCE",
                                   LocalDateTime.now().plusDays(2), "CONFIRMED",
                                   "Regular service", "John Doe", "john@example.com",
                                   "Toyota Camry 2020"));

        bookings.add(new BookingDto(2L, 1L, 2L, "INSPECTION",
                                   LocalDateTime.now().plusDays(5), "PENDING",
                                   "Pre-purchase inspection", "John Doe", "john@example.com",
                                   "Honda Civic 2019"));

        return bookings;
    }

    // ==================== REAL API CALLS (COMMENTED OUT) ====================

    /*
    private void createBookingAPI(BookingDto bookingDto) {
        bookingWebClient.post()
                .__uri__("/__api__/bookings")
                .bodyValue(bookingDto)
                .retrieve()
                .bodyToMono(BookingDto.class)
                .block();
    }

    private List<BookingDto> getUserBookingsAPI(Long userId) {
        return bookingWebClient.get()
                .__uri__("/__api__/bookings/user/" + userId)
                .retrieve()
                .bodyToFlux(BookingDto.class)
                .collectList()
                .block();
    }
    */
}