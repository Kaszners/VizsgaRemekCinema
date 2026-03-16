package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class BookingNotFoundException extends RuntimeException {
    public BookingNotFoundException() {
        super("Nem talalhato ilyen foglalas :o");
    }
}
