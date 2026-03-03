package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class SeatAlreadyBookedException extends RuntimeException {
    public SeatAlreadyBookedException( ) {
        super("Ez a szék már foglalt :p");
    }
}
