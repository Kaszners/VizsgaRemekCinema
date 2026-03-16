package hu.nyirszikszi.vizsgaremek.cinema.dto;



public record SeatAvailabilityResponse(
        Long seatId,
        int row,
        int number,
        String status
) {
}
