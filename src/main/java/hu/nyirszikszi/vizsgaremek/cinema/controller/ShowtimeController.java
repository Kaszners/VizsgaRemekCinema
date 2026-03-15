package hu.nyirszikszi.vizsgaremek.cinema.controller;

import hu.nyirszikszi.vizsgaremek.cinema.dto.SeatAvailabilityResponse;
import hu.nyirszikszi.vizsgaremek.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cinema/showtimes")
public class ShowtimeController {

    private final SeatService seatService;


    @GetMapping("/{showtimeId}/seats")
    public List<SeatAvailabilityResponse> getSeatAvailability(@PathVariable int showtimeId){
        return seatService.getSeatAvailability(showtimeId);
    }

}
