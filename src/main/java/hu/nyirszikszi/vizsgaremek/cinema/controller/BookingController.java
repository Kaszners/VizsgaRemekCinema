package hu.nyirszikszi.vizsgaremek.cinema.controller;

import hu.nyirszikszi.vizsgaremek.cinema.dto.BookingResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateBookingRequest;
import hu.nyirszikszi.vizsgaremek.cinema.service.BookingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cinema/booking")
public class BookingController {

    private final BookingService bookingService;


    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','STAFF','ADMIN')")
    public void bookMovie(@RequestBody CreateBookingRequest request){
        bookingService.createBooking(request);
    }


    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public List<BookingResponse> getCurrentUserBookings(){
        return bookingService.getCurrentUserBookings();
    }


}
