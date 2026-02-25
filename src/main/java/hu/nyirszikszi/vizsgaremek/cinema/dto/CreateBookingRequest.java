package hu.nyirszikszi.vizsgaremek.cinema.dto;

public record CreateBookingRequest(
        int showtimeId,
        int SeatId
) {}
