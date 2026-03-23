package hu.nyirszikszi.vizsgaremek.cinema.dto;

import java.util.List;

public record CreateBookingRequest(
        Long showtimeId,
        List<Long> seatIds
) {}
