package hu.nyirszikszi.vizsgaremek.cinema.dto;

import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingResponse(
        Long bookingId,
        BookingStatus status,
        LocalDateTime created,
        Long showtimeId,
        Long seatId
){}
