package hu.nyirszikszi.vizsgaremek.cinema.enums;

import lombok.Getter;

@Getter
public enum TheaterSize {
    SMALL(5, 8),
    LARGE(10, 12);

    private final int rows;
    private final int columns;


    TheaterSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
