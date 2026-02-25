package hu.nyirszikszi.vizsgaremek.cinema.dto;

import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
        int bookingId,
        BookingStatus status,
        LocalDateTime created,
        int showtimeId,
        int seatId
){}
