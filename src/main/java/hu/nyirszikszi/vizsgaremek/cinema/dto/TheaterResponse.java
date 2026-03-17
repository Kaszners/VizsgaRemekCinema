package hu.nyirszikszi.vizsgaremek.cinema.dto;

import hu.nyirszikszi.vizsgaremek.cinema.enums.TheaterSize;

public record TheaterResponse(
        Long id,
        String name,
        TheaterSize size
) {}
