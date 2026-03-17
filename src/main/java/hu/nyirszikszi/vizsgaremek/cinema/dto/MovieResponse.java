package hu.nyirszikszi.vizsgaremek.cinema.dto;


public record MovieResponse(
        Long id,
        String title,
        int duration,
        String posterUrl,
        String trailerUrl
        ){}
