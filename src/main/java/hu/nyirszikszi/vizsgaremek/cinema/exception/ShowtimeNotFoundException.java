package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class ShowtimeNotFoundException extends RuntimeException {
    public ShowtimeNotFoundException( ) {
        super("Nincs ezen az idoponton vetites :( ");
    }
}
