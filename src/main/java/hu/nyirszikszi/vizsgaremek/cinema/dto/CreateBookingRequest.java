package hu.nyirszikszi.vizsgaremek.cinema.dto;

public record CreateBookingRequest(
        Long showtimeId,
        Long SeatId
) {}
