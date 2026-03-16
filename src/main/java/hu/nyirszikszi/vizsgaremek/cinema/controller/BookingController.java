package hu.nyirszikszi.vizsgaremek.cinema.controller;

import hu.nyirszikszi.vizsgaremek.cinema.dto.BookingResponse;
import hu.nyirszikszi.vizsgaremek.cinema.dto.CreateBookingRequest;
import hu.nyirszikszi.vizsgaremek.cinema.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema/booking")
public class BookingController {

    private final BookingService bookingService;



    @PostMapping
    @PreAuthorize("hasAnyRole('USER','STAFF','ADMIN')")
    public void bookMovie(@RequestBody CreateBookingRequest request){
        bookingService.createBooking(request);
    }


    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','STAFF','ADMIN')")
    public List<BookingResponse> getCurrentUserBookings(){
        return bookingService.getCurrentUserBookings();
    }

    @GetMapping("/confirm/{token}")
    public String confirmBooking(@PathVariable String token){
        bookingService.confirmBooking(token);
        return "Booking confirmed";
    }


}
