package hu.nyirszikszi.vizsgaremek.cinema.dto;

import hu.nyirszikszi.vizsgaremek.cinema.enums.BookingStatus;

public record SeatAvailabilityResponse(
        int seatId,
        int row,
        int number,
        String status
) {
}
