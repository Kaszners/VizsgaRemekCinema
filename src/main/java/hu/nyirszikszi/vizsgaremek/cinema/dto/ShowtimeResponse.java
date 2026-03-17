package hu.nyirszikszi.vizsgaremek.cinema.dto;

import java.time.LocalDateTime;

public record ShowtimeResponse(
        Long id,
        Long movieId,
        String movieTitle,
        Long theaterId,
        String theaterName,
        LocalDateTime showStartTime
) {
}
