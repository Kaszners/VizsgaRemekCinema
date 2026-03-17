package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException( ) {
        super("nincs ilyen film");
    }
}
