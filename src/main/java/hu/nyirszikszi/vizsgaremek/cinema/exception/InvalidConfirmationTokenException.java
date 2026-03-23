package hu.nyirszikszi.vizsgaremek.cinema.exception;

public class InvalidConfirmationTokenException extends RuntimeException {
    public InvalidConfirmationTokenException() {
        super("Ervenytelen confirmation tokent adott meg");
    }
}
