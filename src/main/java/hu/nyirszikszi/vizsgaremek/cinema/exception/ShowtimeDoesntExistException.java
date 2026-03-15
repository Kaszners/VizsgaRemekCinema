package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class ShowtimeDoesntExistException extends RuntimeException {
    public ShowtimeDoesntExistException( ) {
        super("Nincs ezen az idoponton vetites :( ");
    }
}
