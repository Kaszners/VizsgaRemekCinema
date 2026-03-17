package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException( ) {
        super("nincs ilyen ules");
    }
}
